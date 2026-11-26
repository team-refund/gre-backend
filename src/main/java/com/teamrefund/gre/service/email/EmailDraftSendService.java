package com.teamrefund.gre.service.email;

import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.service.gmail.GmailSendService;
import com.teamrefund.gre.service.gmail.GmailSendService.SentMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDraftSendService {
  private final EmailDraftTransactionService transactions;
  private final GmailSendService gmail;

  /** 발송을 선점한 요청만 Gmail에 전달하고 결과를 별도 트랜잭션에 기록합니다. */
  public EmailSendAttempt send(String loginId, long draftId, String requestKey) {
    var claim = transactions.claimSend(loginId, draftId, requestKey);
    if (!claim.shouldSend()) {
      return claim.attempt();
    }

    SentMessage sent;
    try {
      sent =
          gmail.send(
              loginId,
              claim.connection(),
              claim.draft(),
              claim.attachments(),
              claim.attempt().getMessageIdHeader());
    } catch (BusinessException exception) {
      return transactions.recordSendFailure(claim, exception.getErrorCode());
    }
    return transactions.recordSent(claim, sent);
  }
}
