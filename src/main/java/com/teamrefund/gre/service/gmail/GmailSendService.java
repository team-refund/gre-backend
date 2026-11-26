package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;

@Service
public class GmailSendService {
  private final GmailAccessTokenService tokens;
  private final GmailMimeMessageEncoder encoder;
  private final RestClient gmail;

  public GmailSendService(
      GmailAccessTokenService tokens,
      GmailMimeMessageEncoder encoder,
      @Qualifier("gmailApiClient") RestClient gmail) {
    this.tokens = tokens;
    this.encoder = encoder;
    this.gmail = gmail;
  }

  /** 선점한 Gmail 연결로 MIME 메시지를 발송하고 Google이 반환한 메시지와 스레드 ID를 반환합니다. */
  public SentMessage send(
      String loginId,
      GmailConnection expectedConnection,
      EmailDraftRow draft,
      List<EmailDraftRow.Attachment> attachments,
      String messageIdHeader) {
    var accessToken = tokens.getComposeTokenForConnection(loginId, expectedConnection);
    var raw =
        encoder.encode(
            draft.getRecipientEmail(),
            draft.getSubject(),
            draft.getBody(),
            expectedConnection.getGoogleEmail(),
            messageIdHeader,
            attachments);
    try {
      var response =
          gmail
              .post()
              .uri("/gmail/v1/users/me/messages/send")
              .headers(headers -> headers.setBearerAuth(accessToken))
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("raw", raw))
              .retrieve()
              .body(JsonNode.class);
      var id = response == null ? null : response.path("id").asString(null);
      var threadId = response == null ? null : response.path("threadId").asString(null);
      if (id == null || id.isBlank() || threadId == null || threadId.isBlank()) {
        throw new BusinessException(ErrorCode.EMAIL_SEND_UNCONFIRMED);
      }
      return new SentMessage(id, threadId);
    } catch (RestClientResponseException exception) {
      int status = exception.getStatusCode().value();
      if (status == 401) {
        throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
      }
      if (status == 400 || status == 403 || status == 404) {
        throw new BusinessException(ErrorCode.EMAIL_SEND_FAILED);
      }
      throw new BusinessException(ErrorCode.EMAIL_SEND_UNCONFIRMED);
    } catch (RestClientException exception) {
      throw new BusinessException(ErrorCode.EMAIL_SEND_UNCONFIRMED);
    }
  }

  public record SentMessage(String messageId, String threadId) {}
}
