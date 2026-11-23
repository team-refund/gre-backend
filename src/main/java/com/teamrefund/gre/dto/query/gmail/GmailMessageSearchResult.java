package com.teamrefund.gre.dto.query.gmail;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailMessageSearchResult {
  private final List<GmailMessageSummary> messages;
  private final String nextPageToken;
}
