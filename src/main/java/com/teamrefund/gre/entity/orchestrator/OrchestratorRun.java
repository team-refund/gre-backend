package com.teamrefund.gre.entity.orchestrator;

import java.time.Instant;
import lombok.Data;

@Data
public class OrchestratorRun {
  private Long id;
  private Long caseId;
  private String triggerType;
  private String triggerKey;
  private String orchestratorRunsStatus;
  private String nextAction;
  private String resultData;
  private String errorMessage;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant completedAt;
}
