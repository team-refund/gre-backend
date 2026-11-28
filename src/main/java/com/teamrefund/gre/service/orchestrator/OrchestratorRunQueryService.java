package com.teamrefund.gre.service.orchestrator;

import com.teamrefund.gre.dao.orchestrator.OrchestratorRunDao;
import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrchestratorRunQueryService {
  private final OrchestratorRunDao orchestratorRunDao;

  public OrchestratorRun getRun(String loginId, long runId) {
    if (runId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var run = orchestratorRunDao.findOwnedById(runId, loginId);
    if (run == null) {
      throw new BusinessException(ErrorCode.ORCHESTRATOR_RUN_NOT_FOUND);
    }
    return run;
  }
}
