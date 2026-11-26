package com.teamrefund.gre.dto.response.gmail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GmailReconnectResponse {
  private final String authorizationUrl;
}
