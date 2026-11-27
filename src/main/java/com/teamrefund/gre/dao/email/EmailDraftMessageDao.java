package com.teamrefund.gre.dao.email;

import com.teamrefund.gre.entity.email.EmailDraftMessage;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftMessageDao {
  // 초안의 전체 작성 대화를 생성 시각과 ID 순으로 조회한다.
  List<EmailDraftMessage> findByDraftId(@Param("draftId") long draftId);

  // 기존 초안의 ID를 emailDraftId에 담아 사용자 요청 또는 AI 답변 한 건을 저장한다.
  // DB가 생성한 메시지 자체의 ID를 message.id에 설정한다. AI 답변의 ID는 assistantMessageId로 보낸다.
  int insert(EmailDraftMessage message);
}
