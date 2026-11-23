package com.teamrefund.gre.controller.policy;

import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.service.policy.PolicyRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PolicyRagTestController {

  public static final String PATH = "/internal/test/policy-rag";

  private final PolicyRagService policyRagService;

  @GetMapping(PATH)
  public PolicyRagResult reviewPolicy(@RequestParam("question") String question) {
    return policyRagService.reviewPolicy(question, 0.0, "EBAY");
  }
}
