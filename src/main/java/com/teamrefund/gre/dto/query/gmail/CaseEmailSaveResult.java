package com.teamrefund.gre.dto.query.gmail;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CaseEmailSaveResult {
  private final long caseEmailThreadId;
  private final int savedMessageCount;
}
