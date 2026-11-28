package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

@Data
public class EmailDraftAttachment {
  private Long id;
  private Long emailDraftId;
  private Long evidenceId;
  private Instant createdAt;
}
