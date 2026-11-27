package com.teamrefund.gre.dao.orchestrator;

import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrchestratorRunDao {
  // 새 이벤트는 PENDING으로 저장하고 생성된 ID를 run에 채운다. 중복이면 0을 반환한다.
  int insertPending(OrchestratorRun run);

  OrchestratorRun findById(@Param("runId") long runId);

  OrchestratorRun findByTrigger(
      @Param("caseId") long caseId,
      @Param("triggerType") String triggerType,
      @Param("triggerKey") String triggerKey);

  long countByCaseId(@Param("caseId") long caseId);

  List<OrchestratorRun> findPageByCaseId(
      @Param("caseId") long caseId, @Param("limit") int limit, @Param("offset") long offset);

  // 선점한 실행자만 영향받은 행 수 1을 받는다.
  int claimPending(@Param("runId") long runId);

  int updateProgress(@Param("runId") long runId, @Param("resultData") String resultData);

  int markCompleted(
      @Param("runId") long runId,
      @Param("nextAction") String nextAction,
      @Param("resultData") String resultData);

  // 마지막 진행 결과를 유지하고 서비스가 정제한 오류 메시지를 기록한다.
  int markFailed(@Param("runId") long runId, @Param("errorMessage") String errorMessage);
}
