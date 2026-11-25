package com.teamrefund.gre.dto.response.policy;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class PolicyRagResponse {
  SearchStatus searchStatus;
  String searchNote;
  EvidenceStatus evidenceStatus;
  String evidenceNote;
  List<Clause> clauses;
  List<Double> similarityScores;

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

  @Data
  public static class Clause {
    private String quoteEn;
    private String section;
    private Source source;
    private Applicability applicability;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Source {
    private String platform;
    private String site;
    private String policyTitle;
    private String sourceUrl;
  }
}
