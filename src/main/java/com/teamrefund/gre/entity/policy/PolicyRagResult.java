package com.teamrefund.gre.entity.policy;

import lombok.Data;

/** 정책 검색 결과에서 발췌한 원문과 보충 설명을 저장합니다. DB 테이블은 {@code policy_rag_results}입니다. */
@Data
public class PolicyRagResult {
  private Long id;
  private String[] chunkExcerpts;
  private String supplementNote;
}
