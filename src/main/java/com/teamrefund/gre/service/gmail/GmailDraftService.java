package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;

@Service
public class GmailDraftService {
  private final GmailAccessTokenService accessTokenService;
  private final RestClient gmailClient;

  public GmailDraftService(
      GmailAccessTokenService accessTokenService,
      @Qualifier("gmailApiClient") RestClient gmailClient) {
    this.accessTokenService = accessTokenService;
    this.gmailClient = gmailClient;
  }

  public String createDraft(String loginId, String to, String subject, String body) {
    var token = accessTokenService.getComposeToken(loginId);
    var rawMessage = encodeDraft(to, subject, body);
    try {
      var draft =
          gmailClient
              .post()
              .uri("/gmail/v1/users/me/drafts")
              .headers(headers -> headers.setBearerAuth(token))
              .contentType(MediaType.APPLICATION_JSON)
              .body(Map.of("message", Map.of("raw", rawMessage)))
              .retrieve()
              .body(JsonNode.class);
      var draftId = draft == null ? null : draft.path("id").asString(null);
      if (draftId == null || draftId.isBlank()) {
        throw new BusinessException(ErrorCode.GMAIL_DRAFT_CREATE_FAILED);
      }
      return draftId;
    } catch (RestClientResponseException exception) {
      if (exception.getStatusCode().value() == 401) {
        throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
      }
      throw new BusinessException(ErrorCode.GMAIL_DRAFT_CREATE_FAILED);
    } catch (RestClientException exception) {
      throw new BusinessException(ErrorCode.GMAIL_DRAFT_CREATE_FAILED);
    }
  }

  private String encodeDraft(String to, String subject, String body) {
    try {
      var message = new MimeMessage(Session.getInstance(new Properties()));
      var recipient = new InternetAddress(to, true);
      recipient.validate();
      if (recipient.isGroup() || !to.equals(recipient.getAddress())) {
        throw new BusinessException(ErrorCode.INVALID_REQUEST);
      }
      message.setRecipient(Message.RecipientType.TO, recipient);
      message.setSubject(subject, StandardCharsets.UTF_8.name());
      message.setText(body, StandardCharsets.UTF_8.name());
      var output = new ByteArrayOutputStream();
      message.writeTo(output);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(output.toByteArray());
    } catch (MessagingException | IOException exception) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
  }
}
