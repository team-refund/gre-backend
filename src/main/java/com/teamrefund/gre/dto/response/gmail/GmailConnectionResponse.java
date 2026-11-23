package com.teamrefund.gre.dto.response.gmail;

import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GmailConnectionResponse {
  private final GmailConnectionStatus status;
  private final String googleEmail;
  private final Instant connectedAt;
}
