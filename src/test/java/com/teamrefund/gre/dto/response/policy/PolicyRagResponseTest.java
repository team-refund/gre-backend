package com.teamrefund.gre.dto.response.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Applicability;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Review;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.SearchStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

class PolicyRagResponseTest {
  @Test
  void excludesExperimentalFieldsFromResponseAndPreservesReviewStorage() {
    var mapper = JsonMapper.builder().build();
    Review review = new Review();
    review.setChunkId("chunk-1");
    review.setApplicability(Applicability.UNCERTAIN);
    review.setReason("구매일 확인 필요");
    var response =
        new PolicyRagResponse(
            SearchStatus.EVIDENCE_SUFFICIENT,
            "정책 검색 완료",
            List.of(),
            List.of(review),
            "구매일 확인 필요",
            List.of(0.8));

    var json = mapper.readTree(mapper.writeValueAsString(response));
    assertFalse(json.has("reviews"));
    assertFalse(json.has("similarityScores"));
    assertFalse(json.has("searchStatus"));
    assertFalse(json.has("searchNote"));

    var storedReviews = mapper.readTree(mapper.writeValueAsString(response.getReviews()));
    assertEquals("chunk-1", storedReviews.get(0).path("chunkId").asString());
    assertEquals("UNCERTAIN", storedReviews.get(0).path("applicability").asString());
  }
}
