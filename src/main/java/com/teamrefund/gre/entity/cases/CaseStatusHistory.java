package com.teamrefund.gre.entity.cases;

import java.util.Date;
import lombok.Data;

@Data
public class CaseStatusHistory {
  private Long id;
  private Long caseId;
  private String caseTimelineStatus;
  private String description;
  private Date createdAt;
}
