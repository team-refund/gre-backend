package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

@Data
public class EmailDraftMessage {
  private Long id;
  private Long emailDraftId;
  private String role;
  private String content;
  private Instant createdAt;
}
