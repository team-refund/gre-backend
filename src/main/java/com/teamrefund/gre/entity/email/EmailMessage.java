package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 사건에 연결된 Gmail 스레드에서 주고받은 개별 메일을 저장합니다. DB 테이블은 {@code email_messages}입니다. */
@Data
public class EmailMessage {
  private Long id;
  private Long caseEmailThreadId;
  private String gmailMessageId;
  private String senderEmail;
  private String recipientEmail;
  private String subject;
  private String body;
  private String direction;
  private Instant receivedAt;
  private Instant createdAt;
}
