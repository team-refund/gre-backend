package com.teamrefund.gre.service.cases;

import com.teamrefund.gre.dao.cases.CaseDao;
import com.teamrefund.gre.dao.cases.CaseEvidenceDao;
import com.teamrefund.gre.dao.cases.CaseInteractionDao;
import com.teamrefund.gre.dao.cases.CaseStatusHistoryDao;
import com.teamrefund.gre.dao.email.EmailDraftDao;
import com.teamrefund.gre.dao.orchestrator.OrchestratorRunDao;
import com.teamrefund.gre.dto.query.cases.CaseDetailResult;
import com.teamrefund.gre.dto.query.cases.CasePageResult;
import com.teamrefund.gre.entity.cases.Case;
import com.teamrefund.gre.entity.cases.CaseEvidence;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.auth.ActiveUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaseService {
  private static final int MAX_PAGE_SIZE = 100;
  private static final int MAX_STATUS_LENGTH = 30;

  private final ActiveUserService activeUserService;
  private final CaseDao caseDao;
  private final CaseEvidenceDao caseEvidenceDao;
  private final CaseInteractionDao caseInteractionDao;
  private final CaseStatusHistoryDao caseStatusHistoryDao;
  private final EmailDraftDao emailDraftDao;
  private final OrchestratorRunDao orchestratorRunDao;

  public CasePageResult listCases(String loginId, int page, int size, String status) {
    if (page < 0 || size < 1 || size > MAX_PAGE_SIZE) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var statusFilter = status == null || status.isBlank() ? null : status.strip();
    if (statusFilter != null && statusFilter.length() > MAX_STATUS_LENGTH) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var userId = activeUserService.requireActiveUserId(loginId);
    var cases = caseDao.findPageByUserId(userId, statusFilter, size, (long) page * size);
    return new CasePageResult(cases, caseDao.countByUserId(userId, statusFilter));
  }

  public CaseDetailResult getCaseDetail(String loginId, long caseId) {
    var refundCase = requireOwnedCase(loginId, caseId);
    return new CaseDetailResult(
        refundCase,
        findRequirement(caseId),
        orchestratorRunDao.findLatestByCaseId(caseId),
        emailDraftDao.findSummariesByCaseId(caseId),
        caseEvidenceDao.findByCaseIdAndUserId(caseId, refundCase.getUserId()));
  }

  public List<CaseEvidence> listEvidences(String loginId, long caseId) {
    var refundCase = requireOwnedCase(loginId, caseId);
    return caseEvidenceDao.findByCaseIdAndUserId(caseId, refundCase.getUserId());
  }

  public CaseEvidence getEvidence(String loginId, long caseId, long evidenceId) {
    if (evidenceId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    requireOwnedCase(loginId, caseId);
    var evidence = caseEvidenceDao.findByCaseIdAndEvidenceId(caseId, evidenceId);
    if (evidence == null) {
      throw new BusinessException(ErrorCode.CASE_EVIDENCE_NOT_FOUND);
    }
    return evidence;
  }

  private Case requireOwnedCase(String loginId, long caseId) {
    if (caseId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var userId = activeUserService.requireActiveUserId(loginId);
    var refundCase = caseDao.findByIdAndUserId(caseId, userId);
    if (refundCase == null) {
      throw new BusinessException(ErrorCode.CASE_NOT_FOUND);
    }
    return refundCase;
  }

  private String findRequirement(long caseId) {
    var requirement =
        caseInteractionDao.findLatestRequirement(caseId, CaseRequirementService.TRIGGER_KEY_PREFIX);
    return requirement == null ? caseStatusHistoryDao.findRegistrationPrompt(caseId) : requirement;
  }
}
