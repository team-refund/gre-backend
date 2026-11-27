package com.teamrefund.gre.entity.cases;

import java.util.Date;
import lombok.Data;

@Data
public class CaseInteraction {
  private Long id;
  private Long caseId;
  private Long runId;
  private String interactionType;
  private String prompt;
  private String options;
  private String response;
  private String caseInteractionsStatus;
  private Date createdAt;
  private Date respondedAt;
}
