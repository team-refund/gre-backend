package com.teamrefund.gre.entity.community;

import java.util.Date;
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
  private Date createdAt;
  private Date updatedAt;
  private Date resolvedAt;
}
