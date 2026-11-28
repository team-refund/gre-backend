package com.teamrefund.gre.entity.policy;

import java.util.UUID;
import lombok.Data;

/** 정책 문서의 청크 본문, 메타데이터, 임베딩을 저장합니다. DB 테이블은 {@code policy_vector_store}입니다. */
@Data
public class PolicyVectorStore {
  private UUID id;
  private String content;
  private String metadata;
  private float[] embedding;
}
