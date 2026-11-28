package com.teamrefund.gre.entity.cases;

import java.time.Instant;
import java.time.LocalDate;
import lombok.Data;

/** 해외 직구 환불 사건의 주문 정보, 판매자 정보, 진행 상태를 저장합니다. DB 테이블은 {@code cases}입니다. */
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
  private LocalDate purchaseDate;
  private int paymentAmount;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant resolvedAt;
}
