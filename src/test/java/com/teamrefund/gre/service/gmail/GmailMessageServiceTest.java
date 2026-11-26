package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class GmailMessageServiceTest {
  private final GmailConnectionService connectionService = mock(GmailConnectionService.class);
  private final GmailConnectionDao dao = mock(GmailConnectionDao.class);
  private final GmailTokenCipher cipher = mock(GmailTokenCipher.class);
  private final GmailTokenRefreshService refreshService = mock(GmailTokenRefreshService.class);
  private final RestClient gmailClient = mock(RestClient.class);
  private final GmailMessageService service =
      new GmailMessageService(
          new GmailAccessTokenService(connectionService, dao, cipher, refreshService), gmailClient);

  @Test
  void rejectsDisconnectedUserBeforeLoadingAnyToken() {
    var connection = new GmailConnection();
    connection.setStatus(GmailConnectionStatus.DISCONNECTED);
    when(connectionService.getConnection("gmail-test")).thenReturn(connection);

    assertThatThrownBy(() -> service.search("gmail-test", null, null))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception ->
                assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.GMAIL_NOT_CONNECTED));
    verifyNoInteractions(dao, cipher, gmailClient);
  }

  @Test
  void reportsReauthenticationWhenExpiredTokenCannotBeRenewed() {
    var connection = new GmailConnection();
    connection.setUserId(42L);
    connection.setStatus(GmailConnectionStatus.CONNECTED);
    connection.setOauthClientRegistrationId("gmail");
    connection.setOauthPrincipalName("gre-user:42");
    when(connectionService.getConnection("gmail-test")).thenReturn(connection);

    var client = new GmailAuthorizedClient();
    client.setAccessTokenExpiresAt(Instant.EPOCH);
    when(dao.findAuthorizedClient("gre-user:42")).thenReturn(client);
    doThrow(new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED))
        .when(refreshService)
        .refresh(42L, "gmail-test", client);

    assertThatThrownBy(() -> service.search("gmail-test", null, null))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception ->
                assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.GMAIL_REAUTH_REQUIRED));
    verify(refreshService).refresh(42L, "gmail-test", client);
    verifyNoInteractions(cipher, gmailClient);
  }
}
