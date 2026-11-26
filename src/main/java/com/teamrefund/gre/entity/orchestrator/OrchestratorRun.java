package com.teamrefund.gre.entity.orchestrator;

import java.time.Instant;
import lombok.Data;

/** 사건 분석 실행의 트리거, 진행 상태, 결과를 저장합니다. DB 테이블은 {@code orchestrator_runs}입니다. */
@Data
public class OrchestratorRun {
  private Long id;
  private Long caseId;
  private String triggerType;
  private String triggerKey;
  private String orchestratorRunsStatus;
  private String nextAction;
  private String resultData;
  private String errorMessage;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant completedAt;
}
