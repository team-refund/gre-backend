package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 환불 사건과 Gmail 스레드의 연결을 저장합니다. DB 테이블은 {@code case_email_threads}입니다. */
@Data
public class CaseEmailThread {
  private Long id;
  private Long caseId;
  private Long gmailConnectionId;
  private String gmailThreadId;
  private Instant createdAt;
}
