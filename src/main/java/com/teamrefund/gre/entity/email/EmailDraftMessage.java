package com.teamrefund.gre.entity.email;

import java.time.Instant;
import lombok.Data;

/** 이메일 초안을 작성하거나 수정하는 과정의 대화 메시지를 저장합니다. DB 테이블은 {@code email_draft_messages}입니다. */
@Data
public class EmailDraftMessage {
  private Long id;
  private Long emailDraftId;
  private String role;
  private String content;
  private Instant createdAt;
}
