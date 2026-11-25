package com.teamrefund.gre.dto.response;

import java.util.List;

public record PolicyRagResult(
    SearchStatus searchStatus,
    String searchNote,
    EvidenceStatus evidenceStatus,
    String evidenceNote,
    List<Clause> clauses) {
  public enum SearchStatus {
    EVIDENCE_SUFFICIENT,
    EVIDENCE_INSUFFICIENT,
    POLICY_NOT_FOUND,
    NOT_ASSESSED,
    FAILED
  }

  public enum EvidenceStatus {
    SUFFICIENT,
    INSUFFICIENT,
    NOT_ASSESSED
  }

  public enum Applicability {
    APPLICABLE,
    UNCERTAIN,
    NOT_APPLICABLE
  }

  public record Clause(
      String quoteEn, String section, Source source, Applicability applicability) {}

  public record Source(String platform, String site, String policyTitle, String sourceUrl) {}
}
