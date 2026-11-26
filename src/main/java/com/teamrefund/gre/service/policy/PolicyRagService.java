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
      검색된 공식 정책과 확인된 거래 사실로 JSON을 작성하세요. 정책의 조건과 예외를 보존하세요.

      searchStatus는 해결에 필요한 정책·절차가 모두 있으면 EVIDENCE_SUFFICIENT, 일부 빠졌으면
      EVIDENCE_INSUFFICIENT, 관련 정책이 없으면 POLICY_NOT_FOUND입니다. 증빙의 유무는 별개로 판단하세요.
      searchNote는 구매자가 질의의 교환·환불 요구를 이메일로 전달할 때 검색된 정책을
      근거로 사용할 수 있는지 안내하는 한국어 설명입니다. 다음 순서로 작성하세요.
      정책 원문이 직접 뒷받침하는 범위에서만 이메일에 쓸 주장을 제시하세요.
      정책의 적용 대상, 필수 요건, 예외를 확인된 거래 사실과 대조하세요.
      핵심 요건이 미확인이면 조건부 활용으로 표시하고 확인할 사실을 명시하세요.
      절차를 설명하는 문서는 해당 절차의 근거로만 사용하세요. 정책이 정한 요청 가능성,
      판매자 재량, 판매자 의무를 구분하고 원문이 보장하지 않는 교환·환불을 단정하지 마세요.
      원문에 없는 조건이나 결론을 여러 청크를 결합해 만들어 내지 마세요.
      종합 판단: 검색된 청크를 함께 검토해 이메일에서 뒷받침할 수 있는 요구와
      근거가 부족한 요구를 1~2문장으로 설명하세요.
      근거별 활용: 각 청크를 검토해 문서명과 활용 판단(활용 가능, 조건부 활용, 활용 어려움)을
      적으세요. 이메일에서 뒷받침할 구체적인 주장과 핵심 조건, 또는 활용이 어려운 이유를
      한 문장으로 설명하세요. 같은 주장과 조건을 담은 근거는 한 항목으로 묶으세요.
      searchNote에는 청크 번호와 chunkId를 표시하지 마세요.

      검색된 청크의 전체 원문과 출처는 서버가 응답에 담습니다.
      reviews에는 검색된 각 청크의 검토를 제공된 chunkId로 하나씩 담으세요.
      관련성이 없는 청크의 applicability는 NOT_APPLICABLE로 표시하고 reason에 이유를 기록하세요.
      applicability는 적용이 확인되면 APPLICABLE, 사실이 미확인이면 UNCERTAIN, 적용 불가이면 NOT_APPLICABLE입니다.
      reason에는 정책 요건과 확인된 사실을 대조한 이유를 한국어로 적으세요.
      missingInformation에는 해당 조항을 판단하기 위해 추가로 확인할 사실이나 증빙을 적고, 없으면 null로 두세요.
      evidenceGaps에는 분쟁 해결을 위해 추가로 확인할 사실이나 준비할 증빙을 한국어로 통합하세요.
      추가로 확인할 사항이 없으면 null로 두세요. 공식 문서와 거래 사실에 없는 내용은 추정하지 마세요.
      검색된 청크가 없으면 reviews는 빈 배열입니다.
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
          "검색된 정책·안내문이 없어 이메일에 인용할 정책 근거를 제시할 수 없습니다.",
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
