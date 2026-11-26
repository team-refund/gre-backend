package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 이메일 초안에 첨부한 사건 증빙의 참조를 저장합니다. DB 테이블은 {@code email_draft_attachments}입니다. */
@Data
public class EmailDraftAttachment {
  private Long id;
  private Long emailDraftId;
  private Long evidenceId;
  private Instant createdAt;
}
