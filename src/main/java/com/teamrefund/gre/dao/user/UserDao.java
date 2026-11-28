package com.teamrefund.gre.dao.user;

import com.teamrefund.gre.entity.user.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
  // 이메일 인증 여부와 사용자 상태가 null이면 DB 기본값을 사용하고, 생성된 ID를 user에 설정한다.
  int insert(User user);

  User findById(@Param("userId") long userId);

  User findByLoginId(@Param("loginId") String loginId);

  boolean existsActiveUser(@Param("loginId") String loginId);

  List<User> findAllByStatus(
      @Param("usersStatus") String usersStatus,
      @Param("limit") int limit,
      @Param("offset") long offset);

  int updateAccount(User user);

  int updateStatus(@Param("userId") long userId, @Param("usersStatus") String usersStatus);

  int softDelete(@Param("userId") long userId);
}
