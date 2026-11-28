package com.teamrefund.gre.controller.email;

import com.teamrefund.gre.dto.request.email.EmailDraftApprovalRequest;
import com.teamrefund.gre.dto.request.email.EmailSendRequest;
import com.teamrefund.gre.dto.response.email.DraftApprovalResponse;
import com.teamrefund.gre.dto.response.email.EmailDraftResponse;
import com.teamrefund.gre.dto.response.email.EmailSendAttemptResponse;
import com.teamrefund.gre.entity.email.EmailDraftStatus;
import com.teamrefund.gre.entity.email.EmailSendStatus;
import com.teamrefund.gre.service.email.EmailDraftSendService;
import com.teamrefund.gre.service.email.EmailDraftTransactionService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/email-drafts")
@RequiredArgsConstructor
public class EmailDraftController {
  private final EmailDraftTransactionService drafts;
  private final EmailDraftSendService sendService;

  // 최신 이메일 초안의 상세 정보와 미리보기를 조회합니다.
  @GetMapping("/{draftId}")
  public EmailDraftResponse getDraft(Principal principal, @PathVariable("draftId") Long draftId) {
    var details = drafts.getDraft(principal.getName(), draftId);
    return new EmailDraftResponse(details.draft(), details.attachmentEvidenceIds());
  }

  // 현재 이메일 초안의 내용을 직접 수정합니다.
  @PutMapping("/{draftId}")
  public ResponseEntity<Void> updateDraft(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // AI에 초안 작성 또는 수정을 요청하고 결과를 NDJSON으로 받습니다.
  @PostMapping("/{draftId}/messages")
  public ResponseEntity<Void> createMessage(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 이메일 초안 작성 대화 이력 전체를 조회합니다.
  @GetMapping("/{draftId}/messages")
  public ResponseEntity<Void> listMessages(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 현재 내용을 저장하고 초안을 승인합니다.
  @PostMapping("/{draftId}/approve")
  public DraftApprovalResponse approveDraft(
      Principal principal,
      @PathVariable("draftId") Long draftId,
      @Valid @RequestBody(required = false) EmailDraftApprovalRequest request) {
    var savedAt = drafts.approveDraft(principal.getName(), draftId, request);
    return new DraftApprovalResponse(draftId, EmailDraftStatus.FINALIZED, savedAt);
  }

  // 승인된 초안의 Gmail 발송을 요청합니다.
  @PostMapping("/{draftId}/send")
  public ResponseEntity<EmailSendAttemptResponse> sendDraft(
      Principal principal,
      @PathVariable("draftId") Long draftId,
      @Valid @RequestBody(required = false) EmailSendRequest request) {
    var key = request == null ? null : request.getRequestKey();
    var attempt =
        sendService.send(principal.getName(), draftId, key == null || key.isBlank() ? null : key);
    var status =
        attempt.getStatus() == EmailSendStatus.SENDING ? HttpStatus.ACCEPTED : HttpStatus.OK;
    return ResponseEntity.status(status).body(new EmailSendAttemptResponse(attempt));
  }

  // 이메일 발송 시도와 상태를 조회합니다.
  @GetMapping("/{draftId}/send-attempts")
  public List<EmailSendAttemptResponse> listSendAttempts(
      Principal principal, @PathVariable("draftId") Long draftId) {
    return drafts.listAttempts(principal.getName(), draftId).stream()
        .map(EmailSendAttemptResponse::new)
        .toList();
  }
}
