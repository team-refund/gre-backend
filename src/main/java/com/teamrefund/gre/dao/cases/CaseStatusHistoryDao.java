package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.CaseStatusHistory;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseStatusHistoryDao {
  // 사건 등록 이력(history)에 상태(초기값(status)은 REGISTERED로 할거) 저장 +
  // 사건 등록 이력에 사용자 입력(사건 상황 + 요구사항) 저장(usertPrompt)
  default int insertRegistrationHistory(long caseId, String status, String userPrompt) {
    return insertHistory(caseId, status, userPrompt);
  }

  // 사건 상태가 바뀔 때 새 상태와 설명을 타임라인에 추가한다.
  int insertHistory(
      @Param("caseId") long caseId,
      @Param("status") String status,
      @Param("description") String description);

  // 본인 사건의 이력을 최신순으로 조회한다. limit이 null이면 개수를 제한하지 않는다.
  List<CaseStatusHistory> findByCaseIdAndUserId(
      @Param("caseId") long caseId,
      @Param("userId") long userId,
      @Param("limit") Integer limit,
      @Param("offset") long offset);
}
