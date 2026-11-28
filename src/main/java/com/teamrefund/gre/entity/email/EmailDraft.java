package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

@Data
public class EmailDraft {
  private Long id;
  private Long caseId;
  private String purpose;
  private String recipientEmail;
  private String subject;
  private String body;
  private String emailDraftsStatus;
  private Instant createdAt;
  private Instant updatedAt;
}
