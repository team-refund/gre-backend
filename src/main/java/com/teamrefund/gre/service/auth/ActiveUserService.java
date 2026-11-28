package com.teamrefund.gre.service.auth;

import com.teamrefund.gre.dao.user.UserDao;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActiveUserService {
  private final UserDao userDao;

  public long requireActiveUserId(String loginId) {
    if (loginId == null || loginId.isBlank()) {
      throw new BusinessException(ErrorCode.UNAUTHORIZED);
    }
    var user = userDao.findByLoginId(loginId);
    if (user == null || !"ACTIVE".equals(user.getUsersStatus())) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    return user.getId();
  }
}
