package com.teamrefund.gre.dto.response.cases;

import java.time.Instant;
import java.util.List;
import lombok.Value;

@Value
public class CaseListResponse {
  List<CaseSummary> cases;
  int page;
  int size;
  long totalElements;

  @Value
  public static class CaseSummary {
    long id;
    String title;
    String caseType;
    String status;
    String merchantName;
    Instant createdAt;
    Instant updatedAt;
  }
}
