package com.teamrefund.gre.dao.auth;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestLoginDao {
  boolean existsActiveUser(@Param("loginId") String loginId);

  String findActiveUserPasswordHash(@Param("loginId") String loginId);
}
