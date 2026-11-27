package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.Case;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseDao {
  // 로그인 ID로 삭제되지 않은 활성 회원의 ID를 조회(로그인은 완료된걸로 하기로 결정)
  Long findActiveUserId(@Param("loginId") String loginId);

  // 사건등록(DB저장하는거) 메소드
  int insertCase(Case newCase);

  // 등록된 사건 목록 조회
  List<Case> findPageByUserId(
      @Param("userId") long userId,
      @Param("status") String status,
      @Param("limit") int limit,
      @Param("offset") long offset);

  // 사건 숫자 세기
  long countByUserId(@Param("userId") long userId, @Param("status") String status);

  // 사건 id랑 사용자 id가 일치하는 사건이 있는지 확인
  Case findByIdAndUserId(@Param("caseId") long caseId, @Param("userId") long userId);

  // 본인 사건의 DB에 저장된 현재 상태가 expectedStatus와 같으면 newStatus로 변경
  int updateStatus(
      @Param("caseId") long caseId,
      @Param("userId") long userId,
      @Param("expectedStatus") String expectedStatus,
      @Param("newStatus") String newStatus);
}
