package com.teamrefund.gre.dto.response.policy;

import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Clause;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.EvidenceStatus;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.SearchStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRagModelResponse {
  private SearchStatus searchStatus;
  private String searchNote;
  private EvidenceStatus evidenceStatus;
  private String evidenceNote;
  private List<Clause> clauses;
}
