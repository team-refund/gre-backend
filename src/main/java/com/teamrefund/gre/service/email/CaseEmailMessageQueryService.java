package com.teamrefund.gre.service.email;

import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dao.gmail.GmailMessageDao;
import com.teamrefund.gre.dto.query.gmail.CaseEmailMessagePageResult;
import com.teamrefund.gre.dto.query.gmail.CaseEmailMessageResult;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.auth.ActiveUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CaseEmailMessageQueryService {
  private static final int MAX_PAGE_SIZE = 100;

  private final ActiveUserService activeUserService;
  private final CaseEmailDao caseEmailDao;
  private final GmailMessageDao gmailMessageDao;

  public CaseEmailMessagePageResult listMessages(String loginId, long caseId, int page, int size) {
    if (page < 0 || size < 1 || size > MAX_PAGE_SIZE) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    requireOwnedCase(loginId, caseId);
    long offset = (long) page * size;
    var messages = gmailMessageDao.findPageByCaseId(caseId, offset, size);
    long total = gmailMessageDao.countByCaseId(caseId);
    return new CaseEmailMessagePageResult(messages, total);
  }

  public CaseEmailMessageResult getMessage(String loginId, long caseId, long messageId) {
    if (messageId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    requireOwnedCase(loginId, caseId);
    var message = gmailMessageDao.findByCaseIdAndMessageId(caseId, messageId);
    if (message == null) {
      throw new BusinessException(ErrorCode.CASE_EMAIL_MESSAGE_NOT_FOUND);
    }
    return message;
  }

  private void requireOwnedCase(String loginId, long caseId) {
    if (caseId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var userId = activeUserService.requireActiveUserId(loginId);
    if (!caseEmailDao.existsOwnedCase(caseId, userId)) {
      throw new BusinessException(ErrorCode.CASE_NOT_FOUND);
    }
  }
}
