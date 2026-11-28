package com.teamrefund.gre.dto.query.email;

import com.teamrefund.gre.entity.email.EmailDraftStatus;
import java.time.Instant;
import lombok.Data;

@Data
public class EmailDraftRow {
  private long id;
  private long caseId;
  private String purpose;
  private String recipientEmail;
  private String subject;
  private String body;
  private EmailDraftStatus status;
  private Instant updatedAt;

  @Data
  public static class Attachment {
    private String filePath;
    private String originalFilename;
  }
}
