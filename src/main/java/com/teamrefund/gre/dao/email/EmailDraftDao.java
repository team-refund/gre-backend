package com.teamrefund.gre.dao.email;

import com.teamrefund.gre.entity.email.EmailDraft;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftDao {
  // 이메일 작성 에이전트가 만든 최초 초안을 저장하고 생성된 초안 ID를 draft.id에 설정한다.
  int insert(EmailDraft draft);

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
}
