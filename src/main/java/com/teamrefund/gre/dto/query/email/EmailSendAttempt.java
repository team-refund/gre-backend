package com.teamrefund.gre.dto.query.email;

import com.teamrefund.gre.entity.email.EmailSendStatus;
import java.time.Instant;
import lombok.Data;

@Data
public class EmailSendAttempt {
  private long attemptId;
  private long draftId;
  private Long messageId;
  private EmailSendStatus status;
  private String messageIdHeader;
  private String gmailMessageId;
  private String gmailThreadId;
  private Instant sentAt;
  private String safeErrorCode;
}
