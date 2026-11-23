package com.teamrefund.gre.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
  FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
  GMAIL_OAUTH_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "Gmail 연결 설정을 확인할 수 없습니다."),
  GMAIL_OAUTH_INVALID(HttpStatus.BAD_REQUEST, "Gmail 동의 요청이 유효하지 않거나 거절되었습니다."),
  GMAIL_SCOPE_REQUIRED(HttpStatus.BAD_REQUEST, "Gmail 읽기 및 발송 권한이 필요합니다."),
  GMAIL_REFRESH_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Gmail을 다시 연결해 주세요."),
  GMAIL_PROFILE_LOOKUP_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 계정을 확인하지 못했습니다."),
  GMAIL_TOKEN_EXCHANGE_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 인증을 완료하지 못했습니다."),
  GMAIL_NOT_CONNECTED(HttpStatus.CONFLICT, "Gmail을 먼저 연결해 주세요."),
  GMAIL_REAUTH_REQUIRED(HttpStatus.CONFLICT, "Gmail을 다시 연결해 주세요."),
  GMAIL_SEARCH_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 메일을 조회하지 못했습니다.");
  private final HttpStatus status;
  private final String message;
}
