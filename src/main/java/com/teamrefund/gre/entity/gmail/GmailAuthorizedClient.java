package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;
import lombok.ToString;

/** Gmail OAuth 클라이언트의 토큰과 발급·만료 시각을 저장합니다. DB 테이블은 {@code oauth2_authorized_client}입니다. */
@Data
@ToString(onlyExplicitlyIncluded = true)
public class GmailAuthorizedClient {
  private String clientRegistrationId;
  private String principalName;
  private String accessTokenType;
  private byte[] accessTokenValue;
  private Instant accessTokenIssuedAt;
  private Instant accessTokenExpiresAt;
  private String accessTokenScopes;
  private byte[] refreshTokenValue;
  private Instant refreshTokenIssuedAt;
  private Instant createdAt;
}
