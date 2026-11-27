package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.Case;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseDao {
  // 로그인 ID로 삭제되지 않은 활성 회원의 ID를 조회(로그인은 완료된걸로 하기로 결정)
  Long findActiveUserId(@Param("loginId") String loginId);

  // 사건등록(DB저장하는거) 메소드
  int insertCase(Case newCase);

  // 사건 등록 이력(history)에 상태(초기값(status)은 REGISTERED로 할거) 저장 +
  // 사건 등록 이력에 사용자 입력(사건 상황 + 요구사항) 저장(usertPrompt)
  int insertRegistrationHistory(
      @Param("caseId") long caseId,
      @Param("status") String status,
      @Param("userPrompt") String userPrompt);
}
