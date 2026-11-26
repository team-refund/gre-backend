package com.teamrefund.gre.dto.response.policy;

import java.util.List;
import lombok.Data;
import lombok.Value;

@Value
public class PolicyRagResponse {
  SearchStatus searchStatus;
  String searchNote;
  List<Clause> clauses;
  List<Review> reviews;
  String evidenceGaps;
  List<Double> similarityScores;

  public enum SearchStatus {
    EVIDENCE_SUFFICIENT,
    EVIDENCE_INSUFFICIENT,
    POLICY_NOT_FOUND,
    NOT_ASSESSED,
    FAILED
  }

  public enum Applicability {
    APPLICABLE,
    UNCERTAIN,
    NOT_APPLICABLE
  }

  @Data
  public static class Clause {
    private String chunkId;
    private String policyName;
    private String articleTitle;
    private String articleNumber;
    private String originalTextEn;
    private String officialUrl;
  }

  @Data
  public static class Review {
    private String chunkId;
    private Applicability applicability;
    private String reason;
    private String missingInformation;
  }
}
