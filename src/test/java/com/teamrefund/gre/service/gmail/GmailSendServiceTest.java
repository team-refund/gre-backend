package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.json.JsonMapper;

class GmailSendServiceTest {
  @Test
  void sendsStoredDraftDirectlyWithTheClaimedMessageId() {
    var builder = RestClient.builder().baseUrl("https://gmail.googleapis.com");
    var server = MockRestServiceServer.bindTo(builder).build();
    var tokens = mock(GmailAccessTokenService.class);
    var connection = new GmailConnection();
    connection.setGoogleEmail("buyer@example.test");
    var draft = new EmailDraftRow();
    draft.setRecipientEmail("seller@example.test");
    draft.setSubject("Refund request");
    draft.setBody("Please refund this order.");
    when(tokens.getComposeTokenForConnection("buyer", connection)).thenReturn("test-token");
    server
        .expect(requestTo("https://gmail.googleapis.com/gmail/v1/users/me/messages/send"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(header("Authorization", "Bearer test-token"))
        .andExpect(request -> assertMime((MockClientHttpRequest) request))
        .andRespond(
            withSuccess(
                "{\"id\":\"message-1\",\"threadId\":\"thread-1\"}", MediaType.APPLICATION_JSON));

    var service =
        new GmailSendService(tokens, new GmailMimeMessageEncoder("uploads"), builder.build());
    var sent = service.send("buyer", connection, draft, List.of(), "<gre@example.test>");

    assertThat(sent).isEqualTo(new GmailSendService.SentMessage("message-1", "thread-1"));
    server.verify();
  }

  private void assertMime(MockClientHttpRequest request) {
    try {
      var raw =
          JsonMapper.builder().build().readTree(request.getBodyAsString()).path("raw").asString();
      var bytes = Base64.getUrlDecoder().decode(raw);
      var message =
          new MimeMessage(Session.getInstance(new Properties()), new ByteArrayInputStream(bytes));
      assertThat(message.getMessageID()).isEqualTo("<gre@example.test>");
      assertThat(message.getAllRecipients()).singleElement().hasToString("seller@example.test");
      assertThat(message.getSubject()).isEqualTo("Refund request");
      assertThat(message.getContent().toString()).contains("Please refund this order.");
    } catch (Exception exception) {
      throw new AssertionError(exception);
    }
  }
}
