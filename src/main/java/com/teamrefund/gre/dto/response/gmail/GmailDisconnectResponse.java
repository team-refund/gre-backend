package com.teamrefund.gre.dto.response.gmail;

import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GmailDisconnectResponse {
  private final GmailConnectionStatus status;
  private final boolean revokeConfirmed;
}
