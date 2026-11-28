package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 초안 작성 대화 메시지의 임베딩 벡터와 사용한 모델을 저장합니다. DB 테이블은 {@code email_draft_message_embeddings}입니다. */
@Data
public class EmailDraftMessageEmbedding {
  private Long id;
  private Long messageId;
  private String embeddingModel;
  private float[] embedding;
  private Instant embeddedAt;
}
