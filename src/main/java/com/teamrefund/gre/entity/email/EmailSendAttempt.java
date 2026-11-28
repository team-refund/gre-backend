package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

@Data
public class EmailSendAttempt {
  private Long id;
  private Long emailDraftId;
  private Long gmailConnectionId;
  private String requestKey;
  private String messageIdHeader;
  private String emailSendAttemptsStatus;
  private String gmailMessageId;
  private Instant createdAt;
  private Instant updatedAt;
  private String gmailThreadId;
  private Integer attemptCount;
  private String errorMessage;
  private Instant sentAt;
}
