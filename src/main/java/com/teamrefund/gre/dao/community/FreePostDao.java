package com.teamrefund.gre.dao.community;

import com.teamrefund.gre.entity.community.FreePost;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FreePostDao {
  // 새 게시글 저장
  int insertPost(FreePost post);

  // 본인이 작성한 글 수정/저장
  int updatePost(FreePost post);

  // 본인 글에서 삭제 버튼 눌렀을 때
  int deletePost(@Param("postId") long postId, @Param("userId") long userId);

  // 게시판의 최신 글을 최대 limit개 조회. 커뮤니티 홈에서 최근 3개 보여줄 때
  List<FreePost> findLatest(@Param("limit") int limit);

  // 게시글 앞의 offset개를 건너뛰고 최대 limit개 조회. 한페이지 보여주는거
  List<FreePost> findPage(@Param("limit") int limit, @Param("offset") long offset);

  // 게시판의 전체 게시글 수를 조회. 페이지 수 표시할 때 사용
  long countPosts();

  // 게시글 상세 화면 열 때
  FreePost findById(@Param("postId") long postId);

  // 자유게시판 좋아요는 추후 검토
}
