package com.teamrefund.gre.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestLoginRequest {
  @NotBlank
  @Size(max = 50)
  private String loginId;
}
