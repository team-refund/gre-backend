package com.teamrefund.gre.dto.query.gmail;

import java.time.Instant;
import lombok.Data;

@Data
public class CaseEmailMessageResult {
  private Long messageId;
  private Long caseId;
  private String gmailThreadId;
  private String direction;
  private String senderEmail;
  private String recipientEmail;
  private String subject;
  private String body;
  private Instant sentOrReceivedAt;
}
