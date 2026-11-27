package com.teamrefund.gre.entity.cases;

import java.util.Date;
import lombok.Data;

@Data
public class Case {
  private Long id;
  private Long userId;
  private String title;
  private String caseType;
  private String casesStatus;
  private String merchantName;
  private String purchaseSite;
  private String merchantEmail;
  private String orderNumber;
  private String purchaseDate;
  private Long paymentAmount;
  private Date createdAt;
  private Date updatedAt;
  private Date resolvedAt;
}
