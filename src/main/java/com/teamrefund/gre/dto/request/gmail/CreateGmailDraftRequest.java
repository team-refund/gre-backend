package com.teamrefund.gre.dto.request.gmail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGmailDraftRequest {
  @NotBlank
  @Email
  @Size(max = 254)
  @Pattern(regexp = "^[^\\p{Cntrl}]+$")
  private String to;

  @NotBlank
  @Size(max = 200)
  @Pattern(regexp = "^[^\\p{Cntrl}]+$")
  private String subject;

  @NotBlank
  @Size(max = 20000)
  private String body;
}
