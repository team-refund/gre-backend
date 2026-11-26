package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dto.query.gmail.CaseEmailSaveResult;
import com.teamrefund.gre.dto.query.gmail.GmailThreadMessage;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
class GmailThreadServiceTest {
  private final GmailConnectionService connections = mock(GmailConnectionService.class);
  private final GmailAccessTokenService tokens = mock(GmailAccessTokenService.class);
  private final CaseEmailDao dao = mock(CaseEmailDao.class);
  private final CaseEmailStorageService storage = mock(CaseEmailStorageService.class);
  @Captor private ArgumentCaptor<List<GmailThreadMessage>> messages;

  @Test
  void savesMerchantThreadWithDecodedTextAndDirection() {
    var connection = new GmailConnection();
    connection.setUserId(2L);
    connection.setGoogleEmail("buyer@example.test");
    when(connections.getConnection("test2")).thenReturn(connection);
    when(dao.existsOwnedCase(12L, 2L)).thenReturn(true);
    when(tokens.getReadToken("test2")).thenReturn("test-token");
    when(storage.save(eq(12L), eq(connection), eq("thread-1"), anyList()))
        .thenReturn(new CaseEmailSaveResult(55L, 1));
    var builder = RestClient.builder().baseUrl("https://gmail.googleapis.com");
    var server = MockRestServiceServer.bindTo(builder).build();
    server
        .expect(
            requestTo(
                "https://gmail.googleapis.com/gmail/v1/users/me/threads/thread-1?format=full"))
        .andExpect(header("Authorization", "Bearer test-token"))
        .andRespond(
            withSuccess(
                """
            {"id":"thread-1","messages":[{"id":"message-1","internalDate":"1700000000000",
              "labelIds":["SENT"],"payload":{"mimeType":"text/plain","body":{"data":"UmVmdW5k"},
              "headers":[{"name":"From","value":"Buyer <buyer@example.test>"},
              {"name":"To","value":"Seller <seller@example.test>"},{"name":"Subject","value":"Order"}]}}]}
            """,
                MediaType.APPLICATION_JSON));
    var service = new GmailThreadService(connections, tokens, dao, storage, builder.build());

    var result = service.save("test2", 12L, "seller@example.test", "thread-1");

    assertThat(result.getCaseEmailThreadId()).isEqualTo(55L);
    verify(storage).save(eq(12L), eq(connection), eq("thread-1"), messages.capture());
    assertThat(messages.getValue())
        .singleElement()
        .satisfies(
            message -> {
              assertThat(message.getBody()).isEqualTo("Refund");
              assertThat(message.getDirection()).isEqualTo("OUTBOUND");
              assertThat(message.getRecipientEmail()).isEqualTo("seller@example.test");
            });
    server.verify();
  }

  @Test
  void rejectsAnotherUsersCaseBeforeCallingGmail() {
    var connection = new GmailConnection();
    connection.setUserId(2L);
    when(connections.getConnection("test2")).thenReturn(connection);
    var gmail = mock(RestClient.class);
    var service = new GmailThreadService(connections, tokens, dao, storage, gmail);

    assertThatThrownBy(() -> service.save("test2", 12L, "seller@example.test", "thread-1"))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FORBIDDEN));
    verifyNoInteractions(tokens, storage, gmail);
  }
}
