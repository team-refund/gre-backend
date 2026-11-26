package com.teamrefund.gre.dto.query.gmail;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailThreadSearchResult {
  private final List<ThreadSummary> threads;
  private final String nextPageToken;

  @Getter
  @RequiredArgsConstructor
  public static class ThreadSummary {
    private final String gmailThreadId;
    private final String snippet;
  }
}
