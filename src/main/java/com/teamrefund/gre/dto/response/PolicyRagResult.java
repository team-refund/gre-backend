package com.teamrefund.gre.dto.response;

import java.util.List;

public record PolicyRagResult(Status status, List<Clause> clauses, String note) {
  public enum Status {
    COMPLETED,
    NOT_FOUND,
    INSUFFICIENT_FACTS,
    FAILED
  }

  public enum Applicability {
    APPLICABLE,
    UNCERTAIN,
    NOT_APPLICABLE
  }

  public record Clause(
      String quoteEn, String section, Source source, Applicability applicability, String summary) {}

  public record Source(String platform, String site, String policyTitle, String sourceUrl) {}
}
