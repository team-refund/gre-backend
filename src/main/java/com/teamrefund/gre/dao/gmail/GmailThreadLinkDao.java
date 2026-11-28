package com.teamrefund.gre.dao.gmail;

import com.teamrefund.gre.entity.gmail.CaseEmailThread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GmailThreadLinkDao {
  CaseEmailThread findByCaseAndGmailThread(
      @Param("caseId") long caseId,
      @Param("gmailConnectionId") long gmailConnectionId,
      @Param("gmailThreadId") String gmailThreadId);

  // 같은 사건에 이미 연결된 스레드면 0을 반환한다.
  int insert(CaseEmailThread thread);
}
