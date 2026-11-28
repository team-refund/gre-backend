package com.teamrefund.gre.dao.email;

import com.teamrefund.gre.entity.email.EmailSendAttempt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailSendAttemptDao {
  // 발송 상태 및 이력 응답에 사용할 초안별 발송 기록을 조회한다.
  EmailSendAttempt findByDraftId(@Param("draftId") long draftId);

  // 승인된 초안을 잠근 뒤 발송 요청을 SENDING 상태로 등록한다.
  // 이 상태를 DB에 최종 저장한 뒤 Gmail을 호출한다.
  int reserveSend(EmailSendAttempt attempt);

  // 미발송이 확인된 FAILED 기록을 재사용하고 시도 횟수를 늘린다.
  int reserveRetry(@Param("attemptId") long attemptId, @Param("attemptCount") int attemptCount);

  // 발송 여부가 불확실한 시도를 UNKNOWN으로 기록한다.
  int markUnknown(EmailSendAttempt attempt);

  // 미발송이 확인된 시도를 FAILED로 기록한다.
  int markFailed(EmailSendAttempt attempt);

  // 발송 성공이 확인된 시도에 Gmail 식별자와 발송 시각을 기록한다.
  int markSent(EmailSendAttempt attempt);
}
