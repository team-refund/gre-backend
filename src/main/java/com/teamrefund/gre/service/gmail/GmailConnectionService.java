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
  private final GmailConnectionDao gmailConnectionDao;

  public GmailConnection getConnection(String loginId) {
    var connection = gmailConnectionDao.findForActiveUser(loginId);
    if (connection == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    return connection;
  }
}
