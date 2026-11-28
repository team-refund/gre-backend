package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 이메일 발송 요청 키와 처리 상태, 발송 결과를 저장합니다. DB 테이블은 {@code email_send_attempts}입니다. */
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
