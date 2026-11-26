package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.endpoint.RestClientRefreshTokenTokenResponseClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

class GmailTokenRefreshServiceTest {
  private final RestClientRefreshTokenTokenResponseClient refreshClient =
      mock(RestClientRefreshTokenTokenResponseClient.class);
  private final GmailTokenCipher cipher = mock(GmailTokenCipher.class);
  private final GmailTokenStorageService storage = mock(GmailTokenStorageService.class);
  private final GmailTokenRefreshService service =
      new GmailTokenRefreshService(refreshClient, cipher, storage, "test-client", "test-secret");
  private final GmailAuthorizedClient expiredClient = new GmailAuthorizedClient();

  @BeforeEach
  void setUpExpiredToken() {
    var issuedAt = Instant.EPOCH;
    var accessToken = new byte[] {1};
    var refreshToken = new byte[] {2};
    expiredClient.setPrincipalName("gre-user:2");
    expiredClient.setAccessTokenIssuedAt(issuedAt);
    expiredClient.setAccessTokenExpiresAt(issuedAt.plusSeconds(3600));
    expiredClient.setAccessTokenValue(accessToken);
    expiredClient.setRefreshTokenIssuedAt(issuedAt);
    expiredClient.setRefreshTokenValue(refreshToken);
    when(cipher.decrypt(accessToken, "gre-user:2:access")).thenReturn("expired-token");
    when(cipher.decrypt(refreshToken, "gre-user:2:refresh")).thenReturn("refresh-token");
  }

  @Test
  void savesTokensReturnedByGoogle() {
    var tokens =
        OAuth2AccessTokenResponse.withToken("new-token")
            .tokenType(OAuth2AccessToken.TokenType.BEARER)
            .expiresIn(3600)
            .build();
    when(refreshClient.getTokenResponse(any())).thenReturn(tokens);

    service.refresh(2L, "test2", expiredClient);

    verify(storage).saveRefreshedToken(2L, "test2", expiredClient, tokens);
  }

  @Test
  void marksConnectionForReauthorizationWhenRefreshTokenIsRevoked() {
    when(refreshClient.getTokenResponse(any()))
        .thenThrow(new OAuth2AuthorizationException(new OAuth2Error("invalid_grant")));

    assertThatThrownBy(() -> service.refresh(2L, "test2", expiredClient))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception -> {
              assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.GMAIL_REAUTH_REQUIRED);
            });
    verify(storage).markReauthRequired(2L, "test2", expiredClient);
  }

  @Test
  void leavesConnectionUnchangedWhenGoogleFailsTemporarily() {
    when(refreshClient.getTokenResponse(any()))
        .thenThrow(new OAuth2AuthorizationException(new OAuth2Error("temporarily_unavailable")));

    assertThatThrownBy(() -> service.refresh(2L, "test2", expiredClient))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception -> {
              assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.GMAIL_TOKEN_REFRESH_FAILED);
            });
    verifyNoInteractions(storage);
  }
}
