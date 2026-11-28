package com.teamrefund.gre.service.cases;

import com.teamrefund.gre.dao.cases.CaseDao;
import com.teamrefund.gre.dao.cases.CaseStatusHistoryDao;
import com.teamrefund.gre.dto.query.cases.CaseRegistrationResult;
import com.teamrefund.gre.dto.request.cases.CaseRegistrationRequest;
import com.teamrefund.gre.entity.cases.Case;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.auth.ActiveUserService;
import com.teamrefund.gre.service.orchestrator.OrchestratorRunService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CaseRegistrationService {
  private static final String REGISTERED = "REGISTERED";
  private static final String CASE_CREATED = "CASE_CREATED";

  private final ActiveUserService activeUserService;
  private final CaseDao caseDao;
  private final CaseStatusHistoryDao caseStatusHistoryDao;
  private final OrchestratorRunService orchestratorRunService;

  @Transactional
  public CaseRegistrationResult registerCase(String loginId, CaseRegistrationRequest request) {
    var userId = activeUserService.requireActiveUserId(loginId);
    var purchaseSite = request.getPurchaseSite().strip();
    validatePurchaseSite(purchaseSite);

    var refundCase = new Case();
    refundCase.setUserId(userId);
    refundCase.setTitle(request.getMerchantName().strip());
    refundCase.setCaseType(request.getCaseType().name());
    refundCase.setCasesStatus(REGISTERED);
    refundCase.setMerchantName(request.getMerchantName().strip());
    refundCase.setMerchantEmail(request.getMerchantEmail().strip());
    refundCase.setPurchaseSite(purchaseSite);
    refundCase.setOrderNumber(request.getOrderNumber().strip());
    refundCase.setPurchaseDate(request.getPurchaseDate());
    refundCase.setPaymentAmount(request.getPaymentAmount());
    if (caseDao.insertCase(refundCase) != 1 || refundCase.getId() == null) {
      throw new BusinessException(ErrorCode.CASE_REGISTRATION_FAILED);
    }

    long caseId = refundCase.getId();
    if (caseStatusHistoryDao.insertRegistrationHistory(
            caseId, REGISTERED, request.getRequirement().strip())
        != 1) {
      throw new BusinessException(ErrorCode.CASE_REGISTRATION_FAILED);
    }

    var run = orchestratorRunService.enqueue(caseId, CASE_CREATED, Long.toString(caseId));
    if (run == null) {
      throw new BusinessException(ErrorCode.CASE_REGISTRATION_FAILED);
    }
    return new CaseRegistrationResult(caseId, run.getId());
  }

  private void validatePurchaseSite(String purchaseSite) {
    URI uri;
    try {
      uri = URI.create(purchaseSite);
    } catch (IllegalArgumentException exception) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    if (uri.getHost() == null
        || (!"http".equalsIgnoreCase(uri.getScheme())
            && !"https".equalsIgnoreCase(uri.getScheme()))) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
  }
}
