package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailConnectionService {
  private static final long TEST_USER_ID = 1L;
  private static final String TEST_LOGIN_ID = "gmail-test";

  private final GmailConnectionDao gmailConnectionDao;

  public GmailConnection getConnection(String loginId) {
    if (!TEST_LOGIN_ID.equals(loginId)) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    var connection = gmailConnectionDao.findForActiveUser(TEST_USER_ID, loginId);
    if (connection == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    return connection;
  }
}
