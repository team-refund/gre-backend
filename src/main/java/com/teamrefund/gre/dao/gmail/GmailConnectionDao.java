package com.teamrefund.gre.dao.gmail;

import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GmailConnectionDao {
  GmailConnection findForActiveUser(@Param("loginId") String loginId);

  Long lockActiveUser(@Param("userId") long userId, @Param("loginId") String loginId);

  GmailAuthorizedClient findAuthorizedClient(@Param("principalName") String principalName);

  void upsertAuthorizedClient(GmailAuthorizedClient client);

  void upsertConnection(
      @Param("userId") long userId,
      @Param("googleEmail") String googleEmail,
      @Param("principalName") String principalName);
}
