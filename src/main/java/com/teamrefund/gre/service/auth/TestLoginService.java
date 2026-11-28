package com.teamrefund.gre.service.auth;

import com.teamrefund.gre.dao.user.UserDao;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "gre.dev.test-login-enabled", havingValue = "true")
@RequiredArgsConstructor
public class TestLoginService {
  private final UserDao userDao;

  public Authentication createAuthentication(String loginId) {
    if (!userDao.existsActiveUser(loginId)) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    return UsernamePasswordAuthenticationToken.authenticated(loginId, null, List.of());
  }
}
