package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

/** 자유 게시판 게시글의 본문과 조회·좋아요 수를 저장합니다. DB 테이블은 {@code free_posts}입니다. */
@Data
public class FreePost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private Integer viewCount;
  private Integer likeCount;
  private Instant createdAt;
  private Instant updatedAt;
}
