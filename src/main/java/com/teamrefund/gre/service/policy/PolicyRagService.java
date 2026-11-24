package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.config.policy.PolicySourceAdvisor;
import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.dto.response.PolicyRagResult.Status;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolicyRagService {
  private static final String REVIEW_PROMPT =
      """
      제공된 공식 정책과 확인된 거래 사실을 대조해 유효한 JSON을 반환하세요.
      관련 조항이 있으면 status는 COMPLETED, 없으면 NOT_FOUND와 빈 clauses 배열을 반환하세요.
      quoteEn과 source는 제공된 원문과 메타데이터의 값을 그대로 사용하세요.
      조항 제목이나 번호가 없으면 section은 null입니다.
      적용이 확인되면 APPLICABLE, 미확인이면 UNCERTAIN, 적용 불가이면 NOT_APPLICABLE입니다.
      note에는 근거 부족 이유나 확인할 사항을 적고, 없으면 null로 두세요.
      """;

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public PolicyRagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
    this.chatClient = chatClientBuilder.build();
    this.vectorStore = vectorStore;
  }

  public PolicyRagResult reviewPolicy(
      String requirement, double score, int topK, String platform, String site) {
    return reviewWithDocuments(requirement, score, topK, platform, site).result();
  }

  PolicyReview reviewWithDocuments(
      String requirement, double score, int topK, String platform, String site) {
    if (requirement == null || requirement.isBlank()) {
      PolicyRagResult result =
          new PolicyRagResult(
              Status.INSUFFICIENT_FACTS, List.of(), "소비자 요구와 결제 사이트를 확인하세요.");
      return new PolicyReview(result, List.of());
    }

    FilterExpressionBuilder filters = new FilterExpressionBuilder();
    SearchRequest searchRequest =
        SearchRequest.builder()
            .query(requirement)
            .filterExpression(
                filters.and(filters.eq("platform", platform), filters.eq("site", site)).build())
            .similarityThreshold(score)
            .topK(topK)
            .build();

    QuestionAnswerAdvisor policyAdvisor =
        QuestionAnswerAdvisor.builder(vectorStore).searchRequest(searchRequest).build();

    var response =
        chatClient
            .prompt()
            .advisors(policyAdvisor, new PolicySourceAdvisor())
            .system(REVIEW_PROMPT)
            .user(requirement)
            .call()
            .responseEntity(PolicyRagResult.class);

    List<Document> documents =
        response.response() == null
            ? null
            : response.response().getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
    return new PolicyReview(response.entity(), documents == null ? List.of() : documents);
  }

  record PolicyReview(PolicyRagResult result, List<Document> documents) {}
}
