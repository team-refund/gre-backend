package com.teamrefund.gre.dto.response.cases;

import com.teamrefund.gre.dto.response.orchestrator.OrchestratorRunResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.Value;

@Value
public class CaseDetailResponse {
  long id;
  String title;
  String caseType;
  String status;
  String merchantName;
  String merchantEmail;
  String purchaseSite;
  String orderNumber;
  LocalDate purchaseDate;
  BigDecimal paymentAmount;
  Instant createdAt;
  Instant updatedAt;
  Instant resolvedAt;
  String requirement;
  OrchestratorRunResponse latestRun;
  List<EmailDraftSummary> emailDrafts;
  List<CaseEvidenceResponse> evidences;

  @Value
  public static class EmailDraftSummary {
    long id;
    String subject;
    String status;
    Instant updatedAt;
  }
}
