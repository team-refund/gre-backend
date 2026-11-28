package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.ai.PolicyReviewAgent;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyRagService {
  private final PolicyReviewAgent policyReviewAgent;

  public PolicyRagResponse reviewPolicy(String caseAnalysis, String platform) {
    return policyReviewAgent.reviewPolicy(caseAnalysis, platform);
  }
}
