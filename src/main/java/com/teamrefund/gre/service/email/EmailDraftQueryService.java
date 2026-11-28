package com.teamrefund.gre.service.email;

import com.teamrefund.gre.dao.email.EmailDraftDao;
import com.teamrefund.gre.dto.query.email.EmailDraftDetailResult;
import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDraftQueryService {
  private final EmailDraftDao emailDraftDao;

  public EmailDraftDetailResult getDraft(String loginId, long draftId) {
    var draft = requireOwnedDraft(loginId, draftId);
    return new EmailDraftDetailResult(draft, emailDraftDao.findAttachmentEvidenceIds(draftId));
  }

  public EmailSendAttempt getSendAttempt(String loginId, long draftId) {
    validateDraftId(draftId);
    if (!emailDraftDao.existsOwnedDraft(draftId, loginId)) {
      throw new BusinessException(ErrorCode.EMAIL_DRAFT_NOT_FOUND);
    }
    return emailDraftDao.findAttempt(draftId);
  }

  private EmailDraftRow requireOwnedDraft(String loginId, long draftId) {
    validateDraftId(draftId);
    var draft = emailDraftDao.findOwnedDraft(draftId, loginId);
    if (draft == null) {
      throw new BusinessException(ErrorCode.EMAIL_DRAFT_NOT_FOUND);
    }
    return draft;
  }

  private void validateDraftId(long draftId) {
    if (draftId <= 0) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
  }
}
