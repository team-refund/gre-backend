package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

/** 커뮤니티 댓글과 댓글이 달린 게시글의 참조를 저장합니다. DB 테이블은 {@code community_comments}입니다. */
@Data
public class CommunityComment {
  private Long id;
  private Long userId;
  private Long freePostId;
  private Long questionPostId;
  private Long disputePostId;
  private String content;
  private Instant createdAt;
  private Instant updatedAt;
}
