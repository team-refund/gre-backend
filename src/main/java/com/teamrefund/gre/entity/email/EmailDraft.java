package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 사건별 이메일 초안의 용도, 수신자, 본문과 상태를 저장합니다. DB 테이블은 {@code email_drafts}입니다. */
@Data
public class EmailDraft {
  private Long id;
  private Long caseId;
  private String purpose;
  private String recipientEmail;
  private String subject;
  private String body;
  private String emailDraftsStatus;
  private Instant createdAt;
  private Instant updatedAt;
}
