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

  Long upsertCaseThread(
      @Param("caseId") long caseId,
      @Param("connectionId") long connectionId,
      @Param("gmailThreadId") String gmailThreadId);

  int insertMessage(
      @Param("caseThreadId") long caseThreadId, @Param("message") GmailThreadMessage message);
}
