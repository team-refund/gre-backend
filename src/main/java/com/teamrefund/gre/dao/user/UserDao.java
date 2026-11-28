package com.teamrefund.gre.dao.user;

import com.teamrefund.gre.entity.user.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
  int insert(User user);

  User findById(@Param("userId") long userId);

  User findByLoginId(@Param("loginId") String loginId);

  List<User> findByStatus(
      @Param("status") String status, @Param("limit") int limit, @Param("offset") long offset);

  int update(User user);

  int updateStatus(@Param("userId") long userId, @Param("status") String status);

  int softDelete(@Param("userId") long userId);
}
