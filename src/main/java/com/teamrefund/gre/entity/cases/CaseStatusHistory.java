package com.teamrefund.gre.entity.cases;

import java.time.Instant;
import lombok.Data;

/** 사건의 상태 변경 이력과 변경 내용을 저장합니다. DB 테이블은 {@code case_status_histories}입니다. */
@Data
public class CaseStatusHistory {
  private Long id;
  private Long caseId;
  private String caseTimelineStatus;
  private String description;
  private Instant createdAt;
}
