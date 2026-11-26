package com.teamrefund.gre.exception;

import com.teamrefund.gre.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
  public ResponseEntity<ErrorResponse> handleInvalidRequest(HttpServletRequest request) {
    var code = ErrorCode.INVALID_REQUEST;
    return ResponseEntity.status(code.getStatus())
        .body(new ErrorResponse(code, request.getRequestURI()));
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorResponse> handleBusinessException(
      BusinessException exception, HttpServletRequest request) {
    var code = exception.getErrorCode();
    return ResponseEntity.status(code.getStatus())
        .body(new ErrorResponse(code, request.getRequestURI()));
  }
}
