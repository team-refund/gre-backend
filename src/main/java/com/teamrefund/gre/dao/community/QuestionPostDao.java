package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.QuestionPost;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionPostDao {
  List<QuestionPost> findLatest(@Param("limit") int limit);

  List<QuestionPost> findPage(
      @Param("questionStatus") String questionStatus,
      @Param("limit") int limit,
      @Param("offset") long offset);

  long countPosts(@Param("questionStatus") String questionStatus);

  QuestionPost findById(@Param("postId") long postId);

  int insertPost(QuestionPost post);

  int updatePost(QuestionPost post);

  int deletePost(@Param("postId") long postId, @Param("userId") long userId);

  int updateQuestionStatus(
      @Param("postId") long postId,
      @Param("userId") long userId,
      @Param("questionStatus") String questionStatus,
      @Param("checkedCommentId") Integer checkedCommentId);
}
