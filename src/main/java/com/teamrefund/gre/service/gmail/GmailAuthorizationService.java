package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
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
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Service
@Slf4j
public class GmailAuthorizationService {
  private static final Duration AUTHORIZATION_TIMEOUT = Duration.ofMinutes(10);
  private static final Set<String> GMAIL_SCOPES =
      Set.of(
          "https://www.googleapis.com/auth/gmail.readonly",
          "https://www.googleapis.com/auth/gmail.send");

  private final GmailConnectionService gmailConnectionService;
  private final String clientId;
  private final String clientSecret;
  private final String redirectUri;
  private final RestClientAuthorizationCodeTokenResponseClient tokenClient;
  private final RestClient profileClient;
  private final GmailTokenStorageService tokenStorageService;

  public GmailAuthorizationService(
      GmailConnectionService gmailConnectionService,
      RestClientAuthorizationCodeTokenResponseClient tokenClient,
      @Qualifier("gmailApiClient") RestClient profileClient,
      GmailTokenStorageService tokenStorageService,
      @Value("${gre.gmail.oauth.client-id}") String clientId,
      @Value("${gre.gmail.oauth.client-secret}") String clientSecret,
      @Value("${gre.gmail.oauth.redirect-uri}") String redirectUri) {
    this.gmailConnectionService = gmailConnectionService;
    this.tokenClient = tokenClient;
    this.profileClient = profileClient;
    this.tokenStorageService = tokenStorageService;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.redirectUri = redirectUri;
  }

  public OAuth2AuthorizationRequest startAuthorization(String loginId) {
    if (clientId.isBlank() || clientSecret.isBlank()) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
    }
    var connection = gmailConnectionService.getConnection(loginId);
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
                  if (connection.getStatus() != GmailConnectionStatus.CONNECTED) {
                    parameters.put("prompt", "consent");
                  }
                })
            .attributes(
                attributes -> {
                  attributes.put("greUserId", connection.getUserId());
                  attributes.put("startedAt", Instant.now());
                });
    OAuth2AuthorizationRequestCustomizers.withPkce().accept(builder);
    return builder.build();
  }

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

  private String fetchGoogleEmail(String accessToken) {
    try {
      var profile =
          profileClient
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
