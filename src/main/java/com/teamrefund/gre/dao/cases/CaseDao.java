package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.Case;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseDao {
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

  // 본인 사건의 현재 상태가 newStatus와 다르면 변경
  int updateStatus(
      @Param("caseId") long caseId,
      @Param("userId") long userId,
      @Param("newStatus") String newStatus);
}
