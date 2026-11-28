package com.teamrefund.gre.dto.response.orchestrator;

import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import java.time.Instant;
import lombok.Value;

@Value
public class OrchestratorRunResponse {
  long id;
  long caseId;
  String triggerType;
  String status;
  String nextAction;
  Instant createdAt;
  Instant updatedAt;
  Instant completedAt;

  public OrchestratorRunResponse(OrchestratorRun run) {
    id = run.getId();
    caseId = run.getCaseId();
    triggerType = run.getTriggerType();
    status = run.getOrchestratorRunsStatus();
    nextAction = run.getNextAction();
    createdAt = run.getCreatedAt();
    updatedAt = run.getUpdatedAt();
    completedAt = run.getCompletedAt();
  }
}
