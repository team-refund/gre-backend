package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;
import lombok.ToString;

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
