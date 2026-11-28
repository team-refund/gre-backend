package com.teamrefund.gre.dto.response.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;
import lombok.Value;

@Value
public class PolicyRagResponse {
  @JsonIgnore SearchStatus searchStatus;
  @JsonIgnore String searchNote;
  List<Clause> clauses;
  @JsonIgnore List<Review> reviews;
  String evidenceGaps;
  @JsonIgnore List<Double> similarityScores;

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
  @JsonIgnoreProperties({"articleTitle", "articleNumber"})
  public static class Clause {
    private String chunkId;
    private String policyName;
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
