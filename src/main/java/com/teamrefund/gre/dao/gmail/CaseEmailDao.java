package com.teamrefund.gre.dao.gmail;

import com.teamrefund.gre.dto.query.gmail.GmailThreadMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseEmailDao {
  boolean existsOwnedCase(@Param("caseId") long caseId, @Param("userId") long userId);

  Long lockOwnedGmailConnection(
      @Param("caseId") long caseId,
      @Param("userId") long userId,
      @Param("connectionId") long connectionId,
      @Param("googleEmail") String googleEmail);

  // 같은 사건에 이미 연결된 스레드면 기존 연결 ID를 반환한다.
  long upsertCaseThread(
      @Param("caseId") long caseId,
      @Param("connectionId") long connectionId,
      @Param("gmailThreadId") String gmailThreadId);

  int insertMessage(
      @Param("caseThreadId") long caseThreadId, @Param("message") GmailThreadMessage message);
}
