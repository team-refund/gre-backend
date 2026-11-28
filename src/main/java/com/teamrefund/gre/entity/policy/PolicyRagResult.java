package com.teamrefund.gre.entity.policy;

import lombok.Data;

@Data
public class PolicyRagResult {
  private Long id;
  private String clauses;
  private String reviews;
  private String evidenceGaps;
}
