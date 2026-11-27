package com.teamrefund.gre.dao.policy;

import com.teamrefund.gre.entity.policy.PolicyRagResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PolicyRagResultDao {
  // 정책 원문과 검토 결과를 저장하고 생성된 결과 ID를 result.id에 설정한다.
  int insert(PolicyRagResult result);

  // 사건의 완료된 실행 중 정책 검색 결과가 저장된 가장 최근 결과를 조회한다.
  PolicyRagResult findLatestByCaseId(@Param("caseId") long caseId);
}
