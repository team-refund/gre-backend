package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Service
@Slf4j
public class GmailAuthorizationService {
  private static final String GOOGLE_REVOCATION_URI = "https://oauth2.googleapis.com/revoke";
  private static final Duration AUTHORIZATION_TIMEOUT = Duration.ofMinutes(10);
  private static final Set<String> GMAIL_SCOPES =
      Set.of(
          "https://www.googleapis.com/auth/gmail.readonly", GmailAccessTokenService.COMPOSE_SCOPE);

  private final GmailConnectionService gmailConnectionService;
  private final String clientId;
  private final String clientSecret;
  private final String redirectUri;
  private final RestClientAuthorizationCodeTokenResponseClient tokenClient;
  private final RestClient googleClient;
  private final GmailTokenStorageService tokenStorageService;
  private final GmailTokenCipher tokenCipher;

  public GmailAuthorizationService(
      GmailConnectionService gmailConnectionService,
      RestClientAuthorizationCodeTokenResponseClient tokenClient,
      @Qualifier("gmailApiClient") RestClient googleClient,
      GmailTokenStorageService tokenStorageService,
      GmailTokenCipher tokenCipher,
      @Value("${gre.gmail.oauth.client-id}") String clientId,
      @Value("${gre.gmail.oauth.client-secret}") String clientSecret,
      @Value("${gre.gmail.oauth.redirect-uri}") String redirectUri) {
    this.gmailConnectionService = gmailConnectionService;
    this.tokenClient = tokenClient;
    this.googleClient = googleClient;
    this.tokenStorageService = tokenStorageService;
    this.tokenCipher = tokenCipher;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.redirectUri = redirectUri;
  }

  /** 현재 사용자의 Gmail OAuth 동의 요청을 시작합니다. */
  public OAuth2AuthorizationRequest startAuthorization(String loginId) {
    var connection = gmailConnectionService.getConnection(loginId);
    return createAuthorizationRequest(connection.getUserId());
  }

  /** 기존 연결을 확인한 뒤 새 OAuth 동의 요청을 만듭니다. */
  public OAuth2AuthorizationRequest startReconnect(String loginId) {
    var connection = gmailConnectionService.getConnection(loginId);
    if (connection.getId() == null) {
      throw new BusinessException(ErrorCode.GMAIL_NOT_CONNECTED);
    }
    return createAuthorizationRequest(connection.getUserId());
  }

  /** 사용자 검증 정보와 PKCE를 포함한 OAuth 동의 요청을 조립합니다. */
  private OAuth2AuthorizationRequest createAuthorizationRequest(long userId) {
    if (clientId.isBlank() || clientSecret.isBlank()) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
    }
    var builder =
        OAuth2AuthorizationRequest.authorizationCode()
            .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
            .clientId(clientId)
            .redirectUri(redirectUri)
            .state(UUID.randomUUID().toString())
            .scopes(GMAIL_SCOPES)
            .additionalParameters(
                parameters -> {
                  parameters.put("access_type", "offline");
                  parameters.put("prompt", "consent");
                })
            .attributes(
                attributes -> {
                  attributes.put("greUserId", userId);
                  attributes.put("startedAt", Instant.now());
                });
    OAuth2AuthorizationRequestCustomizers.withPkce().accept(builder);
    return builder.build();
  }

  /** 로컬 연결과 토큰을 정리한 뒤 Google 권한 철회를 시도합니다. */
  public boolean disconnect(String loginId) {
    var connection = gmailConnectionService.getConnection(loginId);
    var client = tokenStorageService.disconnect(loginId, connection.getUserId());
    if (client == null) {
      return false;
    }

    try {
      var refreshToken = client.getRefreshTokenValue();
      var token =
          refreshToken == null
              ? tokenCipher.decrypt(
                  client.getAccessTokenValue(), client.getPrincipalName() + ":access")
              : tokenCipher.decrypt(refreshToken, client.getPrincipalName() + ":refresh");
      var form = new LinkedMultiValueMap<String, String>();
      form.add("token", token);
      var result =
          googleClient
              .post()
              .uri(GOOGLE_REVOCATION_URI)
              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
              .body(form)
              .retrieve()
              .toBodilessEntity();
      return result.getStatusCode().value() == HttpStatus.OK.value();
    } catch (BusinessException | RestClientException exception) {
      log.warn("Google OAuth revocation could not be confirmed");
      return false;
    }
  }

  /** 동의를 시작한 사용자와 유효 시간을 확인하고 Google 토큰을 저장합니다. */
  public void exchangeCode(String loginId, OAuth2AuthorizationRequest authorization, String code) {
    var connection = gmailConnectionService.getConnection(loginId);
    Instant startedAt = authorization.getAttribute("startedAt");
    if (!Objects.equals(authorization.getAttribute("greUserId"), connection.getUserId())
        || startedAt == null
        || !startedAt.plus(AUTHORIZATION_TIMEOUT).isAfter(Instant.now())) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_INVALID);
    }

    var registration =
        CommonOAuth2Provider.GOOGLE
            .getBuilder("gmail")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .redirectUri(redirectUri)
            .scope(GMAIL_SCOPES)
            .build();
    var callback =
        OAuth2AuthorizationResponse.success(code)
            .redirectUri(authorization.getRedirectUri())
            .state(authorization.getState())
            .build();
    var grant =
        new OAuth2AuthorizationCodeGrantRequest(
            registration, new OAuth2AuthorizationExchange(authorization, callback));
    try {
      var tokenResponse = tokenClient.getTokenResponse(grant);
      var scopes = tokenResponse.getAccessToken().getScopes();
      if (!scopes.containsAll(GMAIL_SCOPES)) {
        throw new BusinessException(ErrorCode.GMAIL_SCOPE_REQUIRED);
      }
      var googleEmail = fetchGoogleEmail(tokenResponse.getAccessToken().getTokenValue());
      tokenStorageService.save(connection.getUserId(), loginId, googleEmail, tokenResponse);
    } catch (OAuth2AuthorizationException exception) {
      throw new BusinessException(ErrorCode.GMAIL_TOKEN_EXCHANGE_FAILED);
    }
  }

  /** Gmail 프로필에서 연결 계정의 이메일을 확인합니다. */
  private String fetchGoogleEmail(String accessToken) {
    try {
      var profile =
          googleClient
              .get()
              .uri("/gmail/v1/users/me/profile")
              .headers(headers -> headers.setBearerAuth(accessToken))
              .retrieve()
              .body(new ParameterizedTypeReference<Map<String, Object>>() {});
      if (profile != null
          && profile.get("emailAddress") instanceof String email
          && !email.isBlank()) {
        return email;
      }
      log.warn("Gmail profile response did not contain an email address");
    } catch (RestClientResponseException exception) {
      log.warn("Gmail profile request failed with HTTP {}", exception.getStatusCode().value());
      throw new BusinessException(ErrorCode.GMAIL_PROFILE_LOOKUP_FAILED);
    } catch (RestClientException exception) {
      log.warn("Gmail profile request failed: {}", exception.getClass().getSimpleName());
      throw new BusinessException(ErrorCode.GMAIL_PROFILE_LOOKUP_FAILED);
    }
    throw new BusinessException(ErrorCode.GMAIL_PROFILE_LOOKUP_FAILED);
  }
}
