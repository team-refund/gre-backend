package com.teamrefund.gre.dto.request.cases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;

@Data
public class CaseRequirementUpdateRequest {
  @NotNull private UUID requestKey;

  @NotBlank
  @Size(max = 10000)
  private String requirement;
}
