package com.teamrefund.gre.entity.cases;

import java.time.Instant;
import lombok.Data;

/** AI 실행 중 사용자에게 제시한 질문, 선택지, 응답을 저장합니다. DB 테이블은 {@code case_interactions}입니다. */
@Data
public class CaseInteraction {
  private Long id;
  private Long caseId;
  private Long runId;
  private String interactionType;
  private String prompt;
  private String options;
  private String response;
  private String caseInteractionsStatus;
  private Instant createdAt;
  private Instant respondedAt;
}
