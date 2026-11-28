package com.teamrefund.gre.entity.gmail;

import java.time.Instant;
import lombok.Data;

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
