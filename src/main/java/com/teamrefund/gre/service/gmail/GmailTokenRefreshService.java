package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class GmailTokenRefreshService {
  private final RestClientRefreshTokenTokenResponseClient refreshClient;
  private final GmailTokenCipher tokenCipher;
  private final GmailTokenStorageService tokenStorageService;
  private final String clientId;
  private final String clientSecret;

  public GmailTokenRefreshService(
      RestClientRefreshTokenTokenResponseClient refreshClient,
      GmailTokenCipher tokenCipher,
      GmailTokenStorageService tokenStorageService,
      @Value("${gre.gmail.oauth.client-id}") String clientId,
      @Value("${gre.gmail.oauth.client-secret}") String clientSecret) {
    this.refreshClient = refreshClient;
    this.tokenCipher = tokenCipher;
    this.tokenStorageService = tokenStorageService;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  void refresh(long userId, String loginId, GmailAuthorizedClient client) {
    if (client.getRefreshTokenValue() == null) {
      throw requireReauthorization(userId, loginId, client);
    }
    if (clientId.isBlank() || clientSecret.isBlank()) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
    }
    var request = createRefreshRequest(client);
    var tokens = requestRefreshedTokens(userId, loginId, client, request);
    tokenStorageService.saveRefreshedToken(userId, loginId, client, tokens);
  }

  private OAuth2RefreshTokenGrantRequest createRefreshRequest(GmailAuthorizedClient client) {
    var principalName = client.getPrincipalName();
    var scopes = parseScopes(client.getAccessTokenScopes());
    var accessToken =
        new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            tokenCipher.decrypt(client.getAccessTokenValue(), principalName + ":access"),
            client.getAccessTokenIssuedAt(),
            client.getAccessTokenExpiresAt(),
            scopes);
    var refreshToken =
        new OAuth2RefreshToken(
            tokenCipher.decrypt(client.getRefreshTokenValue(), principalName + ":refresh"),
            client.getRefreshTokenIssuedAt());
    var registration =
        CommonOAuth2Provider.GOOGLE
            .getBuilder("gmail")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scope(scopes)
            .build();
    return new OAuth2RefreshTokenGrantRequest(registration, accessToken, refreshToken);
  }

  private OAuth2AccessTokenResponse requestRefreshedTokens(
      long userId,
      String loginId,
      GmailAuthorizedClient client,
      OAuth2RefreshTokenGrantRequest request) {
    try {
      return refreshClient.getTokenResponse(request);
    } catch (OAuth2AuthorizationException exception) {
      if (OAuth2ErrorCodes.INVALID_GRANT.equals(exception.getError().getErrorCode())) {
        throw requireReauthorization(userId, loginId, client);
      }
      throw new BusinessException(ErrorCode.GMAIL_TOKEN_REFRESH_FAILED);
    } catch (RestClientException exception) {
      throw new BusinessException(ErrorCode.GMAIL_TOKEN_REFRESH_FAILED);
    }
  }

  private BusinessException requireReauthorization(
      long userId, String loginId, GmailAuthorizedClient client) {
    tokenStorageService.markReauthRequired(userId, loginId, client);
    return new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
  }

  private Set<String> parseScopes(String scopes) {
    if (scopes == null || scopes.isBlank()) {
      return Set.of();
    }
    return Arrays.stream(scopes.split(",")).collect(Collectors.toUnmodifiableSet());
  }
}
