package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

/** 분쟁 게시글과 구매처, 분쟁 유형, 해결 상태를 저장합니다. DB 테이블은 {@code dispute_posts}입니다. */
@Data
public class DisputePost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private String merchantName;
  private String purchaseSite;
  private String disputeType;
  private String disputePostsResolutionStatus;
  private Integer viewCount;
  private Instant createdAt;
  private Instant updatedAt;
}
