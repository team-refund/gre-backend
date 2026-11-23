package com.teamrefund.gre.exception;

import com.teamrefund.gre.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(
      BusinessException exception, HttpServletRequest request) {
    var code = exception.getErrorCode();
    return ResponseEntity.status(code.getStatus())
        .body(new ErrorResponse(code, request.getRequestURI()));
  }
}
