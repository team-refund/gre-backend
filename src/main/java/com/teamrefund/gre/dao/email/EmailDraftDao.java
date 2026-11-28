package com.teamrefund.gre.dao.email;

import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.dto.query.email.EmailDraftSummary;
import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.entity.email.EmailDraft;
import com.teamrefund.gre.entity.email.EmailDraftStatus;
import com.teamrefund.gre.entity.email.EmailSendStatus;
import java.time.Instant;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftDao {
  // 이메일 작성 에이전트가 만든 최초 초안을 저장하고 생성된 초안 ID를 draft.id에 설정한다.
  int insert(EmailDraft draft);

  List<EmailDraftSummary> findSummariesByCaseId(@Param("caseId") long caseId);

  // 초안 상세 조회에 필요한 현재 내용을 읽는다.
  EmailDraft findById(@Param("draftId") long draftId);

  // 같은 초안의 수정·승인·발송 선점이 겹치지 않도록 처리 전에 초안 행을 잠근다.
  // 같은 초안을 바꾸려는 다른 요청은 현재 DB 변경이 최종 저장되거나 취소될 때까지 기다린다.
  EmailDraft lockById(@Param("draftId") long draftId);

  // 기존 초안의 수신자·제목·본문을 수정하고 재승인이 필요하도록 DRAFT로 되돌린다.
  int updateContent(EmailDraft draft);

  // 사용자가 승인한 DRAFT 초안을 FINALIZED로 바꾼다.
  int updateToFinalized(@Param("draftId") long draftId);

  // 발송 시도와 초안의 SENT 상태를 하나의 DB 작업으로 함께 저장한다.
  int updateToSent(@Param("draftId") long draftId);

  EmailDraftRow findOwnedDraft(@Param("draftId") long draftId, @Param("loginId") String loginId);

  List<Long> findAttachmentEvidenceIds(long draftId);

  EmailDraftRow lockOwnedDraft(@Param("draftId") long draftId, @Param("loginId") String loginId);

  Instant updateDraft(
      @Param("draftId") long draftId,
      @Param("to") String to,
      @Param("subject") String subject,
      @Param("body") String body);

  void deleteAttachments(long draftId);

  int addAttachment(
      @Param("draftId") long draftId,
      @Param("caseId") long caseId,
      @Param("evidenceId") long evidenceId);

  List<EmailDraftRow.Attachment> findAttachments(long draftId);

  boolean existsOwnedDraft(@Param("draftId") long draftId, @Param("loginId") String loginId);

  EmailSendAttempt findAttempt(long draftId);

  int insertSendAttempt(
      @Param("draftId") long draftId,
      @Param("connectionId") long connectionId,
      @Param("requestKey") String requestKey,
      @Param("messageIdHeader") String messageIdHeader);

  void updateDraftStatus(@Param("draftId") long draftId, @Param("status") EmailDraftStatus status);

  int updateAttempt(
      @Param("attemptId") long attemptId,
      @Param("status") EmailSendStatus status,
      @Param("gmailMessageId") String gmailMessageId,
      @Param("gmailThreadId") String gmailThreadId,
      @Param("errorCode") String errorCode);
}
