package com.teamrefund.gre.entity.user;

import java.time.Instant;
import lombok.Data;
import lombok.ToString;

/** 사용자 계정과 이메일 인증 상태를 저장합니다. DB 테이블은 {@code users}입니다. */
@Data
public class User {
  private Long id;
  private String loginId;
  private String email;
  @ToString.Exclude private String passwordHash;
  private Boolean emailVerified;
  private String usersStatus;
  private Instant createdAt;
  private Instant updatedAt;
  private Instant deletedAt;
}
