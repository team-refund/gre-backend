package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.Base64;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

class GmailTokenStorageServiceTest {
  private static final long USER_ID = 42L;
  private static final String LOGIN_ID = "gmail-test";
  private static final String EMAIL = "gmail-test@example.test";

  private final GmailConnectionDao dao = mock(GmailConnectionDao.class);
  private final GmailTokenCipher cipher =
      new GmailTokenCipher(Base64.getEncoder().encodeToString(new byte[32]));
  private final GmailTokenStorageService service = new GmailTokenStorageService(dao, cipher);

  @Test
  void savesEncryptedTokensBeforeReferencingThemFromTheConnection() {
    when(dao.lockActiveUser(USER_ID, LOGIN_ID)).thenReturn(USER_ID);
    var tokens = accessTokenResponse().refreshToken("test-refresh-token").build();

    service.save(USER_ID, LOGIN_ID, EMAIL, tokens);

    var savedClient = ArgumentCaptor.forClass(GmailAuthorizedClient.class);
    var writes = inOrder(dao);
    writes.verify(dao).upsertAuthorizedClient(savedClient.capture());
    writes.verify(dao).upsertConnection(USER_ID, EMAIL, "gre-user:42");
    var client = savedClient.getValue();
    assertThat(cipher.decrypt(client.getAccessTokenValue(), "gre-user:42:access"))
        .isEqualTo("test-access-token");
    assertThat(cipher.decrypt(client.getRefreshTokenValue(), "gre-user:42:refresh"))
        .isEqualTo("test-refresh-token");
  }

  @Test
  void rejectsRefreshTokenReuseAcrossGoogleAccountsWithoutWriting() {
    when(dao.lockActiveUser(USER_ID, LOGIN_ID)).thenReturn(USER_ID);
    var connection = new GmailConnection();
    connection.setStatus(GmailConnectionStatus.CONNECTED);
    connection.setGoogleEmail("other-account@example.test");
    connection.setOauthClientRegistrationId("gmail");
    connection.setOauthPrincipalName("gre-user:42");
    when(dao.findForActiveUser(LOGIN_ID)).thenReturn(connection);
    var tokens = accessTokenResponse().build();

    assertThatThrownBy(() -> service.save(USER_ID, LOGIN_ID, EMAIL, tokens))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception ->
                assertThat(exception.getErrorCode())
                    .isEqualTo(ErrorCode.GMAIL_REFRESH_TOKEN_REQUIRED));
    verify(dao, never()).upsertAuthorizedClient(any());
    verify(dao, never()).upsertConnection(anyLong(), anyString(), anyString());
  }

  @Test
  void clearsConnectionReferenceBeforeDeletingTheStoredClient() {
    var principal = "gre-user:42";
    var connection = new GmailConnection();
    connection.setStatus(GmailConnectionStatus.REAUTH_REQUIRED);
    connection.setOauthClientRegistrationId("gmail");
    connection.setOauthPrincipalName(principal);
    var client = new GmailAuthorizedClient();
    when(dao.lockActiveUser(USER_ID, LOGIN_ID)).thenReturn(USER_ID);
    when(dao.findForActiveUser(LOGIN_ID)).thenReturn(connection);
    when(dao.findAuthorizedClient(principal)).thenReturn(client);

    assertThat(service.disconnect(LOGIN_ID, USER_ID)).isSameAs(client);

    var writes = inOrder(dao);
    writes.verify(dao).disconnect(USER_ID);
    writes.verify(dao).deleteAuthorizedClient(principal);
  }

  @Test
  void skipsTokenDeletionWhenAlreadyDisconnected() {
    var connection = new GmailConnection();
    connection.setStatus(GmailConnectionStatus.DISCONNECTED);
    when(dao.lockActiveUser(USER_ID, LOGIN_ID)).thenReturn(USER_ID);
    when(dao.findForActiveUser(LOGIN_ID)).thenReturn(connection);

    assertThat(service.disconnect(LOGIN_ID, USER_ID)).isNull();

    verify(dao, never()).disconnect(anyLong());
    verify(dao, never()).deleteAuthorizedClient(anyString());
  }

  private OAuth2AccessTokenResponse.Builder accessTokenResponse() {
    return OAuth2AccessTokenResponse.withToken("test-access-token")
        .tokenType(OAuth2AccessToken.TokenType.BEARER)
        .expiresIn(3600)
        .scopes(Set.of("https://www.googleapis.com/auth/gmail.readonly"));
  }
}
