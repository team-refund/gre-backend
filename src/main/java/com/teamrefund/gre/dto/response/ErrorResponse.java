package com.teamrefund.gre.dto.response;

import com.teamrefund.gre.exception.ErrorCode;
import java.time.Instant;
import java.util.Map;
import lombok.Getter;

@Getter
public class ErrorResponse {
  private final String code;
  private final String message;
  private final String action;
  private final boolean retryable = false;
  private final Map<String, String> fieldErrors = null;
  private final String path;
  private final Instant timestamp = Instant.now();

  public ErrorResponse(ErrorCode errorCode, String path) {
    this.code = errorCode.name();
    this.message = errorCode.getMessage();
    this.action =
        switch (errorCode) {
          case GMAIL_NOT_CONNECTED, GMAIL_REAUTH_REQUIRED -> "RECONNECT_GMAIL";
          default -> "SHOW_ERROR";
        };
    this.path = path;
  }
}
