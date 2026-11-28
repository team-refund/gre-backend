package com.teamrefund.gre.service.orchestrator;

import com.teamrefund.gre.dao.orchestrator.OrchestratorRunDao;
import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrchestratorRunService {
  private final OrchestratorRunDao orchestratorRunDao;

  public OrchestratorRun findByTrigger(long caseId, String triggerType, String triggerKey) {
    return orchestratorRunDao.findByTrigger(caseId, triggerType, triggerKey);
  }

  public OrchestratorRun enqueue(long caseId, String triggerType, String triggerKey) {
    var run = new OrchestratorRun();
    run.setCaseId(caseId);
    run.setTriggerType(triggerType);
    run.setTriggerKey(triggerKey);
    if (orchestratorRunDao.insertPending(run) != 1 || run.getId() == null) {
      return null;
    }
    run.setOrchestratorRunsStatus("PENDING");
    return run;
  }
}
