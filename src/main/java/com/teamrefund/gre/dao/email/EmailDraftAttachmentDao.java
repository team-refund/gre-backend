package com.teamrefund.gre.dao.email;

import com.teamrefund.gre.dto.query.email.EmailAttachmentFile;
import com.teamrefund.gre.entity.email.EmailDraftAttachment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailDraftAttachmentDao {
  // Gmail 발송 시 첨부할 증빙의 파일명, 저장 경로와 콘텐츠 타입을 조회한다.
  List<EmailAttachmentFile> findFilesByDraftId(@Param("draftId") long draftId);

  // 초안과 같은 사건에 속한 증빙을 연결한다.
  int insertAttachment(EmailDraftAttachment attachment);

  // 첨부 목록을 교체하기 전에 지정한 초안의 기존 증빙 연결을 모두 지운다.
  // 초안 행을 잠근 뒤 초안 수정, 기존 연결 삭제, 새 연결 추가를 한 번에 저장한다.
  int clearAttachments(@Param("draftId") long draftId);
}
