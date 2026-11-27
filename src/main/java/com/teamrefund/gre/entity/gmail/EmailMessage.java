package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;

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
