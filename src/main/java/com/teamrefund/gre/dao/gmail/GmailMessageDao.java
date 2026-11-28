package com.teamrefund.gre.dao.gmail;

import com.teamrefund.gre.dto.query.gmail.CaseEmailMessageResult;
import com.teamrefund.gre.entity.gmail.EmailMessage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GmailMessageDao {
  // 실제 송수신 메일을 저장한다. 같은 스레드의 Gmail 메시지가 이미 저장됐으면 0을 반환한다.
  int insert(EmailMessage message);

  // 사건별 메일 목록 응답에 필요한 전체 건수를 조회한다.
  long countByCaseId(@Param("caseId") long caseId);

  // 사건의 송수신 메일을 최신순으로 페이지 조회한다.
  List<CaseEmailMessageResult> findPageByCaseId(
      @Param("caseId") long caseId, @Param("offset") long offset, @Param("limit") int limit);
}
