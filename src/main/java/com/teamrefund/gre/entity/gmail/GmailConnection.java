package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;

/** 사용자의 Gmail 연결 상태와 OAuth 인증 정보의 참조를 저장합니다. DB 테이블은 {@code gmail_connections}입니다. */
@Data
public class GmailConnection {
  private Long id;
  private long userId;
  private String googleEmail;
  private GmailConnectionStatus status;
  private String oauthClientRegistrationId;
  private String oauthPrincipalName;
  private Instant connectedAt;
  private Instant updatedAt;
}
