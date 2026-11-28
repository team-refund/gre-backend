package com.teamrefund.gre.service.cases;

import com.teamrefund.gre.dao.cases.CaseDao;
import com.teamrefund.gre.dao.cases.CaseInteractionDao;
import com.teamrefund.gre.dao.cases.CaseStatusHistoryDao;
import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.auth.ActiveUserService;
import com.teamrefund.gre.service.orchestrator.OrchestratorRunService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CaseRequirementService {
  static final String TRIGGER_KEY_PREFIX = "requirement:";

  private static final String USER_INFO_UPDATED = "USER_INFO_UPDATED";
  private static final String ANALYZING = "ANALYZING";

  private final ActiveUserService activeUserService;
  private final CaseDao caseDao;
  private final CaseInteractionDao caseInteractionDao;
  private final CaseStatusHistoryDao caseStatusHistoryDao;
  private final OrchestratorRunService orchestratorRunService;

  @Transactional
  public OrchestratorRun submitRequirementUpdate(
      String loginId, long caseId, UUID requestKey, String requirement) {
    if (caseId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var userId = activeUserService.requireActiveUserId(loginId);
    var refundCase = caseDao.lockByIdAndUserId(caseId, userId);
    if (refundCase == null) {
      throw new BusinessException(ErrorCode.CASE_NOT_FOUND);
    }

    var text = requirement.strip();
    var triggerKey = TRIGGER_KEY_PREFIX + requestKey;
    var existingRun = orchestratorRunService.findByTrigger(caseId, USER_INFO_UPDATED, triggerKey);
    if (existingRun != null) {
      if (!text.equals(
          caseInteractionDao.findRequirementByRunId(
              caseId, existingRun.getId(), TRIGGER_KEY_PREFIX))) {
        throw new BusinessException(ErrorCode.CASE_REQUIREMENT_CONFLICT);
      }
      return existingRun;
    }

    var currentStatus = refundCase.getCasesStatus();
    if ("CLOSED".equals(currentStatus) || "RESOLVED".equals(currentStatus)) {
      throw new BusinessException(ErrorCode.CASE_REQUIREMENT_CONFLICT);
    }

    var run = orchestratorRunService.enqueue(caseId, USER_INFO_UPDATED, triggerKey);
    if (run == null) {
      throw new BusinessException(ErrorCode.CASE_REQUIREMENT_CONFLICT);
    }
    if (caseInteractionDao.insertRequirementUpdate(caseId, run.getId(), "사용자 요구사항 수정", text) != 1) {
      throw new BusinessException(ErrorCode.CASE_REQUIREMENT_CONFLICT);
    }
    if (!ANALYZING.equals(currentStatus)) {
      if (caseDao.updateStatus(caseId, userId, currentStatus, ANALYZING) != 1) {
        throw new BusinessException(ErrorCode.CASE_REQUIREMENT_CONFLICT);
      }
      caseStatusHistoryDao.insertHistory(caseId, ANALYZING, "사용자 요구사항 수정으로 분석을 요청했습니다.");
    }
    return run;
  }
}
