package com.teamrefund.gre.entity.community;

import java.util.Date;
import lombok.Data;

@Data
public class CommunityComment {
  private Long id;
  private Long userId;
  private Long freePostId;
  private Long questionPostId;
  private Long disputePostId;
  private String content;
  private Date createdAt;
  private Date updatedAt;
}
