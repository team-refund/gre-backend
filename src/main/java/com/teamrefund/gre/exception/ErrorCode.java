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
  GMAIL_SCOPE_REQUIRED(HttpStatus.BAD_REQUEST, "Gmail 읽기 및 작성 권한이 필요합니다."),
  GMAIL_REFRESH_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST, "Gmail을 다시 연결해 주세요."),
  GMAIL_PROFILE_LOOKUP_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 계정을 확인하지 못했습니다."),
  GMAIL_TOKEN_EXCHANGE_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 인증을 완료하지 못했습니다."),
  GMAIL_NOT_CONNECTED(HttpStatus.CONFLICT, "Gmail을 먼저 연결해 주세요."),
  GMAIL_REAUTH_REQUIRED(HttpStatus.CONFLICT, "Gmail을 다시 연결해 주세요."),
  GMAIL_TOKEN_REFRESH_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 인증을 갱신하지 못했습니다. 잠시 후 다시 시도해 주세요."),
  GMAIL_SEARCH_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 메일을 조회하지 못했습니다."),
  GMAIL_DRAFT_CREATE_FAILED(HttpStatus.BAD_GATEWAY, "Gmail 초안 저장 결과를 확인하지 못했습니다."),
  GMAIL_THREAD_MERCHANT_MISMATCH(HttpStatus.BAD_REQUEST, "사업자 이메일이 스레드의 송수신 내역에 없습니다."),
  CASE_NOT_FOUND(HttpStatus.NOT_FOUND, "사건을 찾을 수 없습니다."),
  CASE_EMAIL_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "사건 이메일을 찾을 수 없습니다."),
  EMAIL_DRAFT_NOT_FOUND(HttpStatus.NOT_FOUND, "이메일 초안을 찾을 수 없습니다."),
  INVALID_CASE_STATE(HttpStatus.CONFLICT, "현재 상태에서는 이메일 초안을 변경하거나 발송할 수 없습니다."),
  EMAIL_SEND_FAILED(HttpStatus.BAD_GATEWAY, "Gmail에서 이메일 발송을 거절했습니다."),
  EMAIL_SEND_UNCONFIRMED(HttpStatus.BAD_GATEWAY, "이메일 발송 결과를 확인할 수 없습니다."),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 내용을 확인해 주세요.");
  private final HttpStatus status;
  private final String message;
}
