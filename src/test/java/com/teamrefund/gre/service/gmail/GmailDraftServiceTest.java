package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.Base64;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

class GmailDraftServiceTest {
  @Test
  void createsDraftWithUtf8Message() {
    var connectionService = mock(GmailConnectionService.class);
    var dao = mock(GmailConnectionDao.class);
    var cipher = mock(GmailTokenCipher.class);
    var refreshService = mock(GmailTokenRefreshService.class);
    var connection = new GmailConnection();
    connection.setUserId(2L);
    connection.setStatus(GmailConnectionStatus.CONNECTED);
    connection.setOauthClientRegistrationId("gmail");
    connection.setOauthPrincipalName("gre-user:2");
    when(connectionService.getConnection("test2")).thenReturn(connection);

    var client = new GmailAuthorizedClient();
    client.setAccessTokenExpiresAt(Instant.now().plusSeconds(600));
    client.setAccessTokenScopes(GmailAccessTokenService.COMPOSE_SCOPE);
    var encrypted = new byte[] {1};
    client.setAccessTokenValue(encrypted);
    when(dao.findAuthorizedClient("gre-user:2")).thenReturn(client);
    when(cipher.decrypt(encrypted, "gre-user:2:access")).thenReturn("test-access-token");

    var builder = RestClient.builder().baseUrl("https://gmail.googleapis.com");
    var server = MockRestServiceServer.bindTo(builder).build();
    server
        .expect(requestTo("https://gmail.googleapis.com/gmail/v1/users/me/drafts"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header("Authorization", "Bearer test-access-token"))
        .andExpect(request -> assertDraft((MockClientHttpRequest) request))
        .andRespond(withSuccess("{\"id\":\"draft-1\"}", MediaType.APPLICATION_JSON));
    var service =
        new GmailDraftService(
            new GmailAccessTokenService(connectionService, dao, cipher, refreshService),
            builder.build());

    assertThat(service.createDraft("test2", "seller@example.test", "환불 문의", "확인 부탁드립니다."))
        .isEqualTo("draft-1");
    server.verify();
  }

  private void assertDraft(MockClientHttpRequest request) {
    try {
      var json = JsonMapper.builder().build().readTree(request.getBodyAsString());
      var raw = Base64.getUrlDecoder().decode(json.path("message").path("raw").asString());
      var message =
          new MimeMessage(Session.getInstance(new Properties()), new ByteArrayInputStream(raw));
      assertThat(message.getAllRecipients()).hasSize(1);
      assertThat(message.getAllRecipients()[0].toString()).isEqualTo("seller@example.test");
      assertThat(message.getSubject()).isEqualTo("환불 문의");
      assertThat(message.getContent()).isEqualTo("확인 부탁드립니다.");
    } catch (Exception exception) {
      throw new AssertionError("Invalid Gmail draft payload", exception);
    }
  }
}
