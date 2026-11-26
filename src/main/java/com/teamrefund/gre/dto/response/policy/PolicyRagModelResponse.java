package com.teamrefund.gre.dto.response.policy;

import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Review;
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
  private List<Review> reviews;
  private String evidenceGaps;
}
