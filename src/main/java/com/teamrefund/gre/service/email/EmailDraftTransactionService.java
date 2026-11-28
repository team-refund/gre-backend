package com.teamrefund.gre.service.email;

import com.teamrefund.gre.dao.email.EmailDraftDao;
import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dto.query.email.EmailDraftDetailResult;
import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.dto.query.gmail.GmailThreadMessage;
import com.teamrefund.gre.dto.request.email.EmailDraftContentRequest;
import com.teamrefund.gre.entity.email.EmailDraft;
import com.teamrefund.gre.entity.email.EmailDraftStatus;
import com.teamrefund.gre.entity.email.EmailSendStatus;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.gmail.GmailConnectionService;
import com.teamrefund.gre.service.gmail.GmailSendService.SentMessage;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailDraftTransactionService {
  private final EmailDraftDao drafts;
  private final CaseEmailDao caseEmails;
  private final GmailConnectionService connections;

  /** 현재 사용자가 소유한 이메일 초안과 연결된 증빙 ID를 조회합니다. */
  @Transactional(readOnly = true)
  public DraftDetails getDraft(String loginId, long draftId) {
    var draft = drafts.findOwnedDraft(draftId, loginId);
    if (draft == null) {
      throw new BusinessException(ErrorCode.EMAIL_DRAFT_NOT_FOUND);
    }
    return new DraftDetails(draft, drafts.findAttachmentEvidenceIds(draftId));
  }

  public record DraftDetails(EmailDraftRow draft, List<Long> attachmentEvidenceIds) {}

  /** 초안 행을 잠그고 사용자가 확인한 내용과 승인 상태를 함께 저장합니다. */
  @Transactional
  public Instant approveDraft(String loginId, long draftId, EmailDraftContentRequest request) {
    var draft = requireOwnedDraft(loginId, draftId);
    if (draft.getStatus() != EmailDraftStatus.DRAFT) {
      throw new BusinessException(ErrorCode.INVALID_CASE_STATE);
    }
    applyContent(draft, request);
    updateAttachments(draft, request == null ? null : request.getAttachmentEvidenceIds());
    return drafts.updateDraft(
        draftId, draft.getRecipientEmail(), draft.getSubject(), draft.getBody());
  }

  @Transactional
  public EmailDraftDetailResult updateDraft(
      String loginId, long draftId, EmailDraftContentRequest request) {
    if (draftId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var draft = requireOwnedDraft(loginId, draftId);
    applyContent(draft, request);
    var content = new EmailDraft();
    content.setId(draftId);
    content.setRecipientEmail(draft.getRecipientEmail());
    content.setSubject(draft.getSubject());
    content.setBody(draft.getBody());
    if (drafts.updateContent(content) != 1) {
      throw new BusinessException(ErrorCode.INVALID_CASE_STATE);
    }
    updateAttachments(draft, request.getAttachmentEvidenceIds());
    return new EmailDraftDetailResult(
        drafts.findOwnedDraft(draftId, loginId), drafts.findAttachmentEvidenceIds(draftId));
  }

  private void applyContent(EmailDraftRow draft, EmailDraftContentRequest request) {
    if (request != null) {
      draft.setRecipientEmail(request.getTo());
      draft.setSubject(request.getSubject());
      draft.setBody(request.getBody());
    }
    validateContent(draft);
  }

  private void updateAttachments(EmailDraftRow draft, List<Long> evidenceIds) {
    if (evidenceIds == null) {
      return;
    }
    drafts.deleteAttachments(draft.getId());
    for (var evidenceId : evidenceIds.stream().distinct().toList()) {
      if (evidenceId == null
          || drafts.addAttachment(draft.getId(), draft.getCaseId(), evidenceId) != 1) {
        throw new BusinessException(ErrorCode.INVALID_REQUEST);
      }
    }
  }

  /** 승인된 초안의 발송을 선점하고 반복 요청에는 기존 시도를 반환합니다. */
  @Transactional
  public SendClaim claimSend(String loginId, long draftId, String requestKey) {
    var draft = requireOwnedDraft(loginId, draftId);
    var existing = drafts.findAttempt(draftId);
    if (existing != null) {
      return new SendClaim(existing, null, null, List.of());
    }
    if (draft.getStatus() != EmailDraftStatus.FINALIZED) {
      throw new BusinessException(ErrorCode.INVALID_CASE_STATE);
    }
    validateContent(draft);

    var connection = connections.getConnection(loginId);
    if (connection.getStatus() == GmailConnectionStatus.REAUTH_REQUIRED) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    if (connection.getStatus() != GmailConnectionStatus.CONNECTED
        || connection.getId() == null
        || connection.getGoogleEmail() == null) {
      throw new BusinessException(ErrorCode.GMAIL_NOT_CONNECTED);
    }
    var key = requestKey == null ? UUID.randomUUID().toString() : requestKey;
    var messageId = "<" + UUID.randomUUID() + "@mail.gre.invalid>";
    if (drafts.insertSendAttempt(draftId, connection.getId(), key, messageId) != 1) {
      throw new BusinessException(ErrorCode.INVALID_CASE_STATE);
    }
    return new SendClaim(
        drafts.findAttempt(draftId), draft, connection, drafts.findAttachments(draftId));
  }

  /** 발송 시도와 초안을 SENT로 갱신하고 사건 메일을 기록합니다. */
  @Transactional
  public EmailSendAttempt recordSent(SendClaim claim, SentMessage sent) {
    var draft = claim.draft();
    updateSendAttempt(
        claim.attempt().getAttemptId(),
        EmailSendStatus.SENT,
        sent.messageId(),
        sent.threadId(),
        null);
    drafts.updateDraftStatus(draft.getId(), EmailDraftStatus.SENT);
    saveSentCaseMessage(claim, sent);
    return drafts.findAttempt(draft.getId());
  }

  /** 발송 실패와 결과 미확인을 구분해 기록합니다. */
  @Transactional
  public EmailSendAttempt recordSendFailure(SendClaim claim, ErrorCode failure) {
    var status =
        failure == ErrorCode.EMAIL_SEND_UNCONFIRMED
            ? EmailSendStatus.UNKNOWN
            : EmailSendStatus.FAILED;
    updateSendAttempt(claim.attempt().getAttemptId(), status, null, null, failure.name());
    return drafts.findAttempt(claim.draft().getId());
  }

  /** SENDING 상태인 발송 시도에만 결과를 반영합니다. */
  private void updateSendAttempt(
      long attemptId,
      EmailSendStatus status,
      String gmailMessageId,
      String gmailThreadId,
      String errorCode) {
    if (drafts.updateAttempt(attemptId, status, gmailMessageId, gmailThreadId, errorCode) != 1) {
      throw new BusinessException(ErrorCode.INVALID_CASE_STATE);
    }
  }

  /** 발송 메시지를 해당 사건의 Gmail 스레드 이력에 추가합니다. */
  private void saveSentCaseMessage(SendClaim claim, SentMessage sent) {
    var draft = claim.draft();
    var connection = claim.connection();
    var caseThreadId =
        caseEmails.upsertCaseThread(draft.getCaseId(), connection.getId(), sent.threadId());
    caseEmails.insertMessage(
        caseThreadId,
        new GmailThreadMessage(
            sent.messageId(),
            connection.getGoogleEmail(),
            draft.getRecipientEmail(),
            draft.getSubject(),
            draft.getBody(),
            "OUTBOUND",
            Instant.now()));
  }

  /** 초안 소유권을 확인하고 발송 시도를 조회합니다. */
  @Transactional(readOnly = true)
  public List<EmailSendAttempt> listAttempts(String loginId, long draftId) {
    if (!drafts.existsOwnedDraft(draftId, loginId)) {
      throw new BusinessException(ErrorCode.EMAIL_DRAFT_NOT_FOUND);
    }
    var attempt = drafts.findAttempt(draftId);
    return attempt == null ? List.of() : List.of(attempt);
  }

  /** 현재 사용자가 소유한 초안 행을 조회해 잠급니다. */
  private EmailDraftRow requireOwnedDraft(String loginId, long draftId) {
    var draft = drafts.lockOwnedDraft(draftId, loginId);
    if (draft == null) {
      throw new BusinessException(ErrorCode.EMAIL_DRAFT_NOT_FOUND);
    }
    return draft;
  }

  /** 수신자, 제목, 본문이 채워졌는지 확인합니다. */
  private void validateContent(EmailDraftRow draft) {
    if (draft.getRecipientEmail() == null
        || draft.getRecipientEmail().isBlank()
        || draft.getSubject().isBlank()
        || draft.getBody().isBlank()) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
  }

  public record SendClaim(
      EmailSendAttempt attempt,
      EmailDraftRow draft,
      GmailConnection connection,
      List<EmailDraftRow.Attachment> attachments) {
    /** 이 요청이 새 발송을 선점했는지 확인합니다. */
    boolean shouldSend() {
      return draft != null;
    }
  }
}
