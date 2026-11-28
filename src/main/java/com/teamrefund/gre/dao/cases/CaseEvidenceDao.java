package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.CaseEvidence;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseEvidenceDao {
  // 사건에 첨부한 증빙 정보를 등록하고 추가된 행 수를 반환
  int insertEvidence(CaseEvidence evidence);

  // 사건 ID와 증빙 ID로 검토할 증빙자료 한 건을 조회(등록시 자동 검토, 관리자 AI의 도구로서 재검토할때도 사용)
  CaseEvidence findByCaseIdAndEvidenceId(
      @Param("caseId") long caseId, @Param("evidenceId") long evidenceId);

  // 본인 사건에 첨부된 증빙 목록을 최신순으로 조회한다.
  List<CaseEvidence> findByCaseIdAndUserId(
      @Param("caseId") long caseId, @Param("userId") long userId);

  // 증빙자료의 상태: PENDING(저장 완료됬고, 검토 전), PROCESSING(검토 중), COMPLETED(성공), FAILED(실패)
  // 자동 검토를 시작할 때 PENDING 상태인 증빙을 PROCESSING으로 전환할 때 사용.
  int startAnalysis(@Param("caseId") long caseId, @Param("evidenceId") long evidenceId);

  // PROCESSING 상태인 증빙자료에 검토 결과(description)를 저장하고 COMPLETED로 전환할 때 사용
  // 일단, 관리자 AI가 도구로 호출하면 결과는 DB 저장 안할거고, 관리자 AI에게만 전달할 예정
  int completeAnalysis(
      @Param("caseId") long caseId,
      @Param("evidenceId") long evidenceId,
      @Param("description") String description);

  // 자동 검토에 실패하면 PROCESSING 상태인 증빙자료를 FAILED로 전환할 때 사용
  int failAnalysis(@Param("caseId") long caseId, @Param("evidenceId") long evidenceId);
}
