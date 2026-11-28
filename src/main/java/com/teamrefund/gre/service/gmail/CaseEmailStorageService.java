package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dto.query.gmail.CaseEmailSaveResult;
import com.teamrefund.gre.dto.query.gmail.GmailThreadMessage;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CaseEmailStorageService {
  private final CaseEmailDao caseEmailDao;

  @Transactional
  public CaseEmailSaveResult save(
      long caseId,
      GmailConnection connection,
      String gmailThreadId,
      List<GmailThreadMessage> messages) {
    var connectionId =
        caseEmailDao.lockOwnedGmailConnection(
            caseId, connection.getUserId(), connection.getId(), connection.getGoogleEmail());
    if (connectionId == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }

    var caseThreadId = caseEmailDao.upsertCaseThread(caseId, connectionId, gmailThreadId);

    int savedMessageCount = 0;
    for (var message : messages) {
      savedMessageCount += caseEmailDao.insertMessage(caseThreadId, message);
    }
    return new CaseEmailSaveResult(caseThreadId, savedMessageCount);
  }
}
