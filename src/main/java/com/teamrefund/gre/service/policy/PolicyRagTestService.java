package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.dto.response.PolicyRagBatchEvaluationResponse;
import com.teamrefund.gre.dto.response.PolicyRagEvaluationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Service
@RequiredArgsConstructor
public class PolicyRagTestService {
  private final PolicyRagService policyRagService;
  private final ChatClient.Builder chatClientBuilder;
  private final JsonMapper jsonMapper;

  public PolicyRagBatchEvaluationResponse evaluateBatch(
      List<String> questions, double similarityThreshold, int topK, String platform) {
    int relevancyPassed = 0;
    int factCheckingPassed = 0;
    int bothPassed = 0;
    for (String question : questions) {
      var evaluation = evaluate(question, similarityThreshold, topK, platform);
      relevancyPassed += evaluation.relevancyPassed() ? 1 : 0;
      factCheckingPassed += evaluation.factCheckingPassed() ? 1 : 0;
      bothPassed += evaluation.relevancyPassed() && evaluation.factCheckingPassed() ? 1 : 0;
    }
    int total = questions.size();
    return new PolicyRagBatchEvaluationResponse(
        total,
        relevancyPassed,
        factCheckingPassed,
        bothPassed,
        100.0 * relevancyPassed / total,
        100.0 * factCheckingPassed / total,
        100.0 * bothPassed / total);
  }

  public PolicyRagEvaluationResponse evaluate(
      String question, double similarityThreshold, int topK, String platform) {
    var review =
        policyRagService.reviewWithDocuments(question, similarityThreshold, topK, platform);
    var evidence =
        review.documents().stream()
            .map(document -> new Document(document.getMetadata() + "\n" + document.getText()))
            .toList();
    var request =
        new EvaluationRequest(question, evidence, jsonMapper.writeValueAsString(review.result()));
    boolean relevancyPassed = new RelevancyEvaluator(chatClientBuilder).evaluate(request).isPass();
    boolean factCheckingPassed =
        FactCheckingEvaluator.builder(chatClientBuilder).build().evaluate(request).isPass();

    return new PolicyRagEvaluationResponse(
        review.result(), review.documents().size(), relevancyPassed, factCheckingPassed);
  }
}
