package com.teamrefund.gre.dto.query.email;

import java.time.Instant;
import lombok.Value;

@Value
public class EmailDraftSummary {
  long id;
  String subject;
  String status;
  Instant updatedAt;
}
