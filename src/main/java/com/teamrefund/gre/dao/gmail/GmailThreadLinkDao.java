package com.teamrefund.gre.dao.gmail;

import com.teamrefund.gre.entity.gmail.CaseEmailThread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GmailThreadLinkDao {
  // 기존 Gmail 스레드를 찾아 연결된 사건과 내부 스레드 ID를 확인한다.
  CaseEmailThread findByGmailThread(
      @Param("gmailConnectionId") long gmailConnectionId,
      @Param("gmailThreadId") String gmailThreadId);

  // 사건과 Gmail 스레드를 연결한다. 이미 같은 Gmail 연결에 저장된 스레드면 0을 반환한다.
  int insert(CaseEmailThread thread);
}
