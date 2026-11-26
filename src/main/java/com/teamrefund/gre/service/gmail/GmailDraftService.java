package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.Map;
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
  private final GmailMimeMessageEncoder encoder;

  public GmailDraftService(
      GmailAccessTokenService accessTokenService,
      @Qualifier("gmailApiClient") RestClient gmailClient,
      GmailMimeMessageEncoder encoder) {
    this.accessTokenService = accessTokenService;
    this.gmailClient = gmailClient;
    this.encoder = encoder;
  }

  /** Gmail 임시보관함에 수신자, 제목, 본문을 담은 초안을 만듭니다. */
  public String createDraft(String loginId, String to, String subject, String body) {
    var token = accessTokenService.getComposeToken(loginId);
    var rawMessage = encoder.encode(to, subject, body);
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
}
