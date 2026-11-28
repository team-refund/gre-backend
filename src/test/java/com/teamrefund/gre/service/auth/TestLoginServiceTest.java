package com.teamrefund.gre.service.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.teamrefund.gre.dao.user.UserDao;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import org.junit.jupiter.api.Test;

class TestLoginServiceTest {
  private final UserDao userDao = mock(UserDao.class);
  private final TestLoginService testLoginService = new TestLoginService(userDao);

  @Test
  void createsAuthenticationForRequestedActiveUser() {
    var loginId = "another-test-user";
    when(userDao.existsActiveUser(loginId)).thenReturn(true);

    var authentication = testLoginService.createAuthentication(loginId);

    assertThat(authentication.isAuthenticated()).isTrue();
    assertThat(authentication.getName()).isEqualTo(loginId);
    assertThat(authentication.getCredentials()).isNull();
    assertThat(authentication.getAuthorities()).isEmpty();
  }

  @Test
  void rejectsLoginWhenNoActiveUserMatches() {
    var loginId = "unavailable-user";
    when(userDao.existsActiveUser(loginId)).thenReturn(false);

    assertThatThrownBy(() -> testLoginService.createAuthentication(loginId))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception -> assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.FORBIDDEN));
  }
}
