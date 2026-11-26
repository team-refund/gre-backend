package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.CommunityComment;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityCommentDao {
  List<CommunityComment> findPageByPost(
      @Param("boardType") String boardType,
      @Param("postId") long postId,
      @Param("limit") int limit,
      @Param("offset") long offset);

  long countByPost(@Param("boardType") String boardType, @Param("postId") long postId);

  CommunityComment findById(@Param("commentId") long commentId);

  int insertComment(@Param("comment") CommunityComment comment);

  int updateComment(
      @Param("commentId") long commentId,
      @Param("userId") long userId,
      @Param("content") String content);

  int deleteComment(@Param("commentId") long commentId, @Param("userId") long userId);
}
