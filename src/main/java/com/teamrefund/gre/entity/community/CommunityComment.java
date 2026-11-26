package com.teamrefund.gre.entity.community;

import java.time.Instant;
import lombok.Data;

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
