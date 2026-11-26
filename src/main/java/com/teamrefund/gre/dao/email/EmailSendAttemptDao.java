package com.teamrefund.gre.dao.email;

import java.time.Instant;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailSendAttemptDao {
  /** DB 제약에 따라 초안당 최대 한 건의 발송 상태를 조회합니다. */
  Map<String, Object> findByDraftId(@Param("draftId") long draftId);

  /** 승인된 초안의 행을 잠근 트랜잭션에서 처음 발송을 선점합니다. */
  int reserveSend(
      @Param("draftId") long draftId,
      @Param("gmailConnectionId") long gmailConnectionId,
      @Param("requestKey") String requestKey,
      @Param("messageIdHeader") String messageIdHeader);

  /** 승인된 초안의 행을 잠근 뒤 미발송이 확인된 FAILED 행을 재사용합니다. 시도 횟수를 증가시키고 기존 요청 키와 Message-ID를 유지합니다. */
  int reserveRetry(@Param("attemptId") long attemptId, @Param("attemptCount") int attemptCount);

  int markUnknown(
      @Param("attemptId") long attemptId,
      @Param("attemptCount") int attemptCount,
      @Param("errorMessage") String errorMessage);

  /** 미발송이 확인된 경우에만 FAILED로 전환합니다. */
  int markFailed(
      @Param("attemptId") long attemptId,
      @Param("attemptCount") int attemptCount,
      @Param("errorMessage") String errorMessage);

  /** 해당 시도의 발송이 확인되면 Gmail 식별자와 발송 시각을 기록합니다. */
  int markSent(
      @Param("attemptId") long attemptId,
      @Param("attemptCount") int attemptCount,
      @Param("gmailMessageId") String gmailMessageId,
      @Param("gmailThreadId") String gmailThreadId,
      @Param("sentAt") Instant sentAt);
}
