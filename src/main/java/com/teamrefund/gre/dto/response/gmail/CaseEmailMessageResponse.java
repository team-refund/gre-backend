package com.teamrefund.gre.dto.response.gmail;

import com.teamrefund.gre.dto.query.gmail.CaseEmailMessageResult;
import java.time.Instant;
import lombok.Value;

@Value
public class CaseEmailMessageResponse {
  long messageId;
  long caseId;
  String gmailThreadId;
  String direction;
  String from;
  String to;
  String subject;
  String body;
  Instant sentOrReceivedAt;

  public CaseEmailMessageResponse(CaseEmailMessageResult message) {
    messageId = message.getMessageId();
    caseId = message.getCaseId();
    gmailThreadId = message.getGmailThreadId();
    direction = message.getDirection();
    from = message.getSenderEmail();
    to = message.getRecipientEmail();
    subject = message.getSubject();
    body = message.getBody();
    sentOrReceivedAt = message.getSentOrReceivedAt();
  }
}
