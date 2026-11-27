package com.teamrefund.gre.entity.community;

import java.util.Date;
import lombok.Data;

@Data
public class FreePost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private int viewCount;
  private int likeCount;
  private Date createdAt;
  private Date updatedAt;
}
