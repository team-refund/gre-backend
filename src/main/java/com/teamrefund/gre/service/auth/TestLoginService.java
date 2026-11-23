package com.teamrefund.gre.service.auth;

import com.teamrefund.gre.dao.auth.TestLoginDao;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "gre.dev.test-login-enabled", havingValue = "true")
@RequiredArgsConstructor
public class TestLoginService {
  private static final String GMAIL_TEST_AUTHORITY = "ROLE_GMAIL_TEST";

  private final TestLoginDao testLoginDao;

  public Authentication createAuthentication(String loginId) {
    if (!testLoginDao.existsActiveUser(loginId)) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    return UsernamePasswordAuthenticationToken.authenticated(
        loginId, null, List.of(new SimpleGrantedAuthority(GMAIL_TEST_AUTHORITY)));
  }
}
