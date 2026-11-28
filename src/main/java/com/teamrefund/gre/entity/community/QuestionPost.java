package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

/** 질문 게시글과 해결 상태, 채택 댓글을 저장합니다. DB 테이블은 {@code question_posts}입니다. */
@Data
public class QuestionPost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private String questionCategory;
  private String questionPostsQuestionStatus;
  private Integer viewCount;
  private Integer checkedCommentId;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant resolvedAt;
}
