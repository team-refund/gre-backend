package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.DisputePost;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DisputePostDao {
  List<DisputePost> findLatest(@Param("limit") int limit);

  List<DisputePost> findPage(@Param("limit") int limit, @Param("offset") long offset);

  long countPosts();

  DisputePost findById(@Param("postId") long postId);

  int insertPost(DisputePost post);

  int updatePost(DisputePost post);

  int deletePost(@Param("postId") long postId, @Param("userId") long userId);
}
