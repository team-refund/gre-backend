package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

@Data
public class QuestionPost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private String questionCategory;
  private String questionStatus;
  private int viewCount;
  private Integer checkedCommentId;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant resolvedAt;
}
