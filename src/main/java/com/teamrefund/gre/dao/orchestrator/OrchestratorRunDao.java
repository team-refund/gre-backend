package com.teamrefund.gre.dao.orchestrator;

import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrchestratorRunDao {
  // 새 실행을 PENDING으로 등록하고 생성된 ID를 run.id에 채운다. 같은 이벤트의 실행이 있으면 0을 반환한다.
  int insertPending(OrchestratorRun run);

  // 실행 ID로 현재 상태, 진행 결과, 다음 작업과 오류를 조회한다.
  OrchestratorRun findById(@Param("runId") long runId);

  OrchestratorRun findOwnedById(@Param("runId") long runId, @Param("loginId") String loginId);

  // 이벤트가 이미 접수됐을 때 사건 ID, 실행 계기, 이벤트 키로 기존 실행을 찾는다.
  OrchestratorRun findByTrigger(
      @Param("caseId") long caseId,
      @Param("triggerType") String triggerType,
      @Param("triggerKey") String triggerKey);

  // 사건 상세의 Run 카드에 표시할 가장 최근 실행을 조회한다. 실행이 없으면 null을 반환한다.
  OrchestratorRun findLatestByCaseId(@Param("caseId") long caseId);

  // 대기 중인 실행을 PROCESSING으로 바꾼다. 1을 반환받은 요청만 실행을 시작한다.
  int claimPending(@Param("runId") long runId);

  // 실행 중에 현재까지의 작업 결과를   저장한다.
  int updateProgress(@Param("runId") long runId, @Param("resultData") String resultData);

  // 실행이 성공하면 최종 결과와 다음 작업을 저장하고 완료 시각을 기록한다.
  int markCompleted(
      @Param("runId") long runId,
      @Param("nextAction") String nextAction,
      @Param("resultData") String resultData);

  // 실행이 실패하면 이전 진행 결과를 유지하고 정제된 오류 메시지를 기록한다.
  int markFailed(@Param("runId") long runId, @Param("errorMessage") String errorMessage);
}
