package com.teamrefund.gre.dao.cases;

import com.teamrefund.gre.entity.cases.CaseEvidence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CaseEvidenceDao {
  // 사건에 증빙 파일 정보를 등록하고 DB가 생성한 ID를 evidence.id에 설정한다.
  int insert(CaseEvidence evidence);

  // 사건과 증빙 ID로 등록된 파일 정보를 조회한다.
  CaseEvidence findById(@Param("caseId") long caseId, @Param("evidenceId") long evidenceId);
}
