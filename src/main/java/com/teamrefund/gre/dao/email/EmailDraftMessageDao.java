package com.teamrefund.gre.dao.email;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftMessageDao {
  /** 초안의 대화 이력 전체를 생성 시각과 ID 순으로 조회합니다. */
  List<Map<String, Object>> findByDraftId(@Param("draftId") long draftId);

  /** 작성 대화 메시지를 저장하고 생성된 메시지 ID를 반환합니다. */
  long insertMessage(
      @Param("draftId") long draftId, @Param("role") String role, @Param("content") String content);
}
