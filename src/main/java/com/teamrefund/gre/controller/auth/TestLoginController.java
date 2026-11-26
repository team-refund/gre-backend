package com.teamrefund.gre.controller.auth;

import com.teamrefund.gre.dto.request.auth.TestLoginRequest;
import com.teamrefund.gre.service.auth.TestLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dev/test-login")
@ConditionalOnProperty(name = "gre.dev.test-login-enabled", havingValue = "true")
@RequiredArgsConstructor
public class TestLoginController {
  private final TestLoginService testLoginService;
  private final SecurityContextRepository securityContextRepository;

  // 개발용 테스트 계정의 로그인 세션을 생성합니다.
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> login(
      @Valid @RequestBody TestLoginRequest loginRequest,
      HttpServletRequest request,
      HttpServletResponse response) {
    var authentication = testLoginService.createAuthentication(loginRequest.getLoginId());

    var previousSession = request.getSession(false);
    if (previousSession != null) {
      previousSession.invalidate();
    }
    var securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    securityContextRepository.saveContext(securityContext, request, response);
    return ResponseEntity.noContent().build();
  }
}
