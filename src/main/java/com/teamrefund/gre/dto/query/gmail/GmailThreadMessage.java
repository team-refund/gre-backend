package com.teamrefund.gre.dto.query.gmail;

import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailThreadMessage {
  private final String gmailMessageId;
  private final String senderEmail;
  private final String recipientEmail;
  private final String subject;
  private final String body;
  private final String direction;
  private final Instant receivedAt;
}
