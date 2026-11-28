package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.CaseInteraction;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseInteractionDao {
  // AI가 사건에 대해 사용자에게 추가 질문을 남길 때 사용
  int insertInteraction(CaseInteraction interaction);

  int insertRequirementUpdate(
      @Param("caseId") long caseId,
      @Param("runId") long runId,
      @Param("prompt") String prompt,
      @Param("requirement") String requirement);

  String findLatestRequirement(
      @Param("caseId") long caseId, @Param("triggerKeyPrefix") String triggerKeyPrefix);

  String findRequirementByRunId(
      @Param("caseId") long caseId,
      @Param("runId") long runId,
      @Param("triggerKeyPrefix") String triggerKeyPrefix);

  // 본인 사건의 질문과 답변 목록 조회. 상태가 있으면 해당 상태만 가져옴
  List<CaseInteraction> findByCaseIdAndUserId(
      @Param("caseId") long caseId,
      @Param("userId") long userId,
      @Param("status") String status,
      @Param("limit") Integer limit,
      @Param("offset") long offset);

  // 목록과 같은 조건으로 질문 수를 셈. 페이지 수를 계산할 때 사용
  long countByCaseIdAndUserId(
      @Param("caseId") long caseId, @Param("userId") long userId, @Param("status") String status);

  // 사용자가 본인 사건의 답변 대기 중인 질문에 답변을 제출할 때 사용
  int answerInteraction(
      @Param("caseId") long caseId,
      @Param("interactionId") long interactionId,
      @Param("userId") long userId,
      @Param("response") String response);
}
