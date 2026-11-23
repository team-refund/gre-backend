package com.teamrefund.gre.dto.query.gmail;

import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailMessageSummary {
  private final String messageId;
  private final String threadId;
  private final String from;
  private final String subject;
  private final String snippet;
  private final Instant receivedAt;
}
