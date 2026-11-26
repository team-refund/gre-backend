package com.teamrefund.gre.dao.email;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftAttachmentDao {
  List<Long> findEvidenceIdsByDraftId(@Param("draftId") long draftId);

  /** 초안과 같은 사건의 증빙을 연결하고 추가된 행 수를 반환합니다. */
  int insertAttachment(@Param("draftId") long draftId, @Param("evidenceId") long evidenceId);

  /** 초안 행을 잠그고 초안 수정과 같은 트랜잭션에서 호출합니다. */
  int deleteByDraftId(@Param("draftId") long draftId);
}
