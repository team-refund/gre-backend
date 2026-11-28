package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.CaseInteraction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseInteractionDao {
  // AI가 사건에 대해 사용자에게 추가 질문을 남길 때 사용
  int insertInteraction(CaseInteraction interaction);

  // 본인 사건에서 해당 Run이 생성한 최신 질문과 답변을 조회
  CaseInteraction findLatestByRunIdAndUserId(
      @Param("runId") long runId, @Param("userId") long userId);

  // 사용자가 본인 사건의 답변 대기 중인 질문에 답변을 제출할 때 사용
  int answerInteraction(
      @Param("caseId") long caseId,
      @Param("interactionId") long interactionId,
      @Param("userId") long userId,
      @Param("response") String response);
}
