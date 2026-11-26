package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.config.policy.PolicySourceAdvisor;
import com.teamrefund.gre.dto.response.policy.PolicyRagModelResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Clause;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Review;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.SearchStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

@Service
public class PolicyRagService {
  private static final String REVIEW_PROMPT =
      """
      검색된 공식 정책과 확인된 거래 사실만으로 JSON을 작성하세요. 설명은 한국어로 작성하고 정책의 요건과 예외를 반영하세요.

      searchStatus는 거래 증빙의 유무와 별개로, 필요한 정책·절차가 모두 있으면 EVIDENCE_SUFFICIENT,
      일부만 있으면 EVIDENCE_INSUFFICIENT, 관련 정책이 없으면 POLICY_NOT_FOUND입니다.
      searchNote에는 질의에 나온 교환·환불 요구를 판매자에게 할 때 검색된 청크를 근거로 쓸 수 있는지
      1~2문장으로 요약하세요. 뒷받침되는 요구와 미확인 핵심 조건 또는 근거가 부족한 이유를 적으세요.
      요청 절차, 판매자 재량, 판매자 의무를 구분하고 원문이 보장하지 않는 결과를 단정하지 마세요.
      청크별 목록과 청크 번호·chunkId는 생략하세요.

      reviews에는 제공된 chunkId마다 검토를 하나씩 담으세요. 검색 결과가 없으면 빈 배열입니다.
      applicability는 적용 확인 시 APPLICABLE, 핵심 사실 미확인 시 UNCERTAIN,
      적용 불가 또는 무관하면 NOT_APPLICABLE입니다. reason에는 정책 요건과 거래 사실을 대조한 근거를 적으세요.
      missingInformation에는 조항 판단에 필요한 추가 사실·증빙을, evidenceGaps에는 분쟁 해결에 필요한
      추가 사실·증빙을 종합해 적으세요. 각각 추가 확인 사항이 없으면 null로 두세요.
      """;

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public PolicyRagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
    this.chatClient = chatClientBuilder.build();
    this.vectorStore = vectorStore;
  }

  public PolicyRagResponse reviewPolicy(
      String requirement, double score, int topK, String platform) {
    SearchRequest searchRequest =
        SearchRequest.builder()
            .query(requirement)
            .filterExpression(new FilterExpressionBuilder().eq("platform", platform).build())
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
            .responseEntity(PolicyRagModelResponse.class);

    List<Document> documents =
        response.response().getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
    if (documents.isEmpty()) {
      return new PolicyRagResponse(
          SearchStatus.POLICY_NOT_FOUND,
          "검색된 정책·안내문이 없어 판매자에게 교환·환불을 요구할 정책 근거를 찾지 못했습니다.",
          List.of(),
          List.of(),
          null,
          List.of());
    }

    List<Double> similarityScores = new ArrayList<>();
    List<Clause> clauses = new ArrayList<>();
    Set<String> retrievedIds = new HashSet<>();
    for (Document document : documents) {
      retrievedIds.add(document.getId());
      similarityScores.add(document.getScore());
      var metadata = document.getMetadata();
      Clause clause = new Clause();
      clause.setChunkId(document.getId());
      clause.setPolicyName((String) metadata.get("policyTitle"));
      clause.setArticleTitle((String) metadata.get("articleTitle"));
      clause.setArticleNumber((String) metadata.get("articleNumber"));
      clause.setOriginalTextEn(document.getText());
      clause.setOfficialUrl((String) metadata.get("sourceUrl"));
      clauses.add(clause);
    }

    PolicyRagModelResponse modelResponse = response.entity();
    List<Review> modelReviews = modelResponse.getReviews();
    if (modelReviews == null) {
      modelReviews = List.of();
    }
    List<Review> reviews = new ArrayList<>();
    for (Review review : modelReviews) {
      if (review != null && retrievedIds.contains(review.getChunkId())) {
        reviews.add(review);
      }
    }

    return new PolicyRagResponse(
        modelResponse.getSearchStatus(),
        modelResponse.getSearchNote(),
        clauses,
        reviews,
        modelResponse.getEvidenceGaps(),
        similarityScores);
  }
}
