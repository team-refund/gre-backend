package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.CommunityComment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityCommentDao {
  // 새 댓글 저장
  int insertComment(@Param("comment") CommunityComment comment);

  // 본인이 본인이 작성한 댓글(작성자 ID가 일치) 내용을 수정할 때 사용
  int updateComment(
      @Param("commentId") long commentId,
      @Param("userId") long userId,
      @Param("content") String content);

  // 본인이 작성한 댓글을 삭제
  int deleteComment(@Param("commentId") long commentId, @Param("userId") long userId);

  // 게시판 유형과 게시글 ID로 댓글 목록을 조회
  List<CommunityComment> findPageByPost(
      @Param("boardType") String boardType,
      @Param("postId") long postId,

      // 댓글 페이징 할 때 사용할거. 일단 만들어 두고, 댓글 페이징 없을 것 같으면 안쓸 예정
      @Param("limit") int limit, // 몇개 가져올지
      @Param("offset") long offset); // 숫자 셀 떄 기준

  // 해당 게시글에 달린 전체 댓글이 몇개인지 조회// 댓글 수 보여주는거 안될 것 같으면 안쓸 예정
  long countByPost(@Param("boardType") String boardType, @Param("postId") long postId);

  // 댓글 한 건을 조회
  CommunityComment findById(@Param("commentId") long commentId);
}
