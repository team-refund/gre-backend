package com.teamrefund.gre.dto.response.gmail;

import java.time.Instant;
import java.util.List;
import lombok.Value;

@Value
public class GmailMessageSearchResponse {
  List<Message> messages;
  String nextPageToken;

  @Value
  public static class Message {
    String gmailMessageId;
    String gmailThreadId;
    String from;
    String subject;
    String snippet;
    Instant receivedAt;
  }
}
