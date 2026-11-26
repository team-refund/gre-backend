package com.teamrefund.gre.dto.request.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDraftApprovalRequest {
  @NotBlank
  @Email
  @Size(max = 255)
  @Pattern(regexp = "^[^\\p{Cntrl}]+$")
  private String to;

  @NotBlank
  @Size(max = 255)
  @Pattern(regexp = "^[^\\p{Cntrl}]+$")
  private String subject;

  @NotBlank
  @Size(max = 20000)
  private String body;

  private List<@NotNull @Positive Long> attachmentEvidenceIds;
}
