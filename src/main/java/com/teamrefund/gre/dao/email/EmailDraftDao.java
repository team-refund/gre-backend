package com.teamrefund.gre.dao.email;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftDao {
  Map<String, Object> findById(@Param("draftId") long draftId);

  List<Map<String, Object>> findByCaseId(@Param("caseId") long caseId);

  /** 수정, 승인, 발송 선점에 앞서 서비스 트랜잭션에서 초안 행을 잠급니다. */
  Map<String, Object> lockById(@Param("draftId") long draftId);

  /** 새 초안을 DRAFT 상태로 저장하고 생성된 초안 ID를 반환합니다. */
  long insertDraft(
      @Param("caseId") long caseId,
      @Param("purpose") String purpose,
      @Param("recipientEmail") String recipientEmail,
      @Param("subject") String subject,
      @Param("body") String body);

  /** 발송 중이거나 결과 확인 중인 초안은 제외하고, 수정 시 상태를 DRAFT로 전환합니다. */
  int updateContent(
      @Param("draftId") long draftId,
      @Param("recipientEmail") String recipientEmail,
      @Param("subject") String subject,
      @Param("body") String body);

  /** 잠근 초안이 DRAFT인 경우 FINALIZED로 전환하고 갱신된 행 수를 반환합니다. */
  int approveDraft(@Param("draftId") long draftId);

  /** 발송 시도와 Gmail 메시지를 저장하는 트랜잭션에서 초안을 SENT로 전환합니다. */
  int markSent(@Param("draftId") long draftId);
}
