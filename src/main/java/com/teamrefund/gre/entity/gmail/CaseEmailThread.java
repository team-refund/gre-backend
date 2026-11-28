package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;

@Data
public class CaseEmailThread {
  private Long id;
  private Long caseId;
  private Long gmailConnectionId;
  private String gmailThreadId;
  private Instant createdAt;
}
