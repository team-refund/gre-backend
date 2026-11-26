package com.teamrefund.gre.dto.response.email;

import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.entity.email.EmailSendStatus;
import java.time.Instant;
import lombok.Getter;

@Getter
public class EmailSendAttemptResponse {
  private final long attemptId;
  private final long draftId;
  private final EmailSendStatus status;
  private final String gmailMessageId;
  private final String gmailThreadId;
  private final Instant sentAt;
  private final String safeErrorCode;

  public EmailSendAttemptResponse(EmailSendAttempt attempt) {
    this.attemptId = attempt.getAttemptId();
    this.draftId = attempt.getDraftId();
    this.status = attempt.getStatus();
    this.gmailMessageId = attempt.getGmailMessageId();
    this.gmailThreadId = attempt.getGmailThreadId();
    this.sentAt = attempt.getSentAt();
    this.safeErrorCode = attempt.getSafeErrorCode();
  }
}
