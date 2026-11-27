package com.teamrefund.gre.entity.community;

import java.util.Date;
import lombok.Data;

@Data
public class DisputePost {
  private Long id;
  private Long userId;
  private String title;
  private String content;
  private String merchantName;
  private String purchaseSite;
  private String disputeType;
  private String resolutionStatus;
  private int viewCount;
  private Date createdAt;
  private Date updatedAt;
}
