package com.teamrefund.gre.dto.request.email;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSendRequest {
  @Size(max = 255)
  private String requestKey;
}
