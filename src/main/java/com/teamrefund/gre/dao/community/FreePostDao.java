package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.FreePost;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FreePostDao {
  List<FreePost> findLatest(@Param("limit") int limit);

  List<FreePost> findPage(@Param("limit") int limit, @Param("offset") long offset);

  long countPosts();

  FreePost findById(@Param("postId") long postId);

  int insertPost(FreePost post);

  int updatePost(FreePost post);

  int deletePost(@Param("postId") long postId, @Param("userId") long userId);
}
