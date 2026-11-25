package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.config.policy.PolicySourceAdvisor;
import com.teamrefund.gre.dto.response.policy.PolicyRagModelResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.EvidenceStatus;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.SearchStatus;
import java.util.ArrayList;
import java.util.List;
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
      검색된 공식 정책·안내문과 확인된 거래 사실로 JSON을 작성하세요. 확인된 범위에서 조건과 예외를 설명하세요.

      searchStatus는 필요한 정책·절차가 모두 있으면 EVIDENCE_SUFFICIENT, 일부 빠졌으면 EVIDENCE_INSUFFICIENT,
      관련 정책이 없으면 POLICY_NOT_FOUND입니다. 거래 사실의 누락은 evidenceStatus에서 판단하세요.
      evidenceStatus는 사실·증빙이 조건을 충족하면 SUFFICIENT, 부족하거나 미확인이면 INSUFFICIENT,
      판단 기준이 검색되지 않았으면 NOT_ASSESSED입니다.

      두 노트는 한국어로 각 항목을 한 줄씩 쓰세요.
      searchNote는 "검색된 정책·안내문:"에 문서명과 확인한 조건·절차, "빠진 근거:"에 빠진 조항·절차,
      "실험 분기 단서:"에 문맥 잘림·적용 범위 불일치 등 관찰과 확인할 사항을 적으세요. 원인은 단정하지 마세요.
      evidenceNote는 "확인된 사실·증빙:"과 "추가로 준비할 증빙:"에 충족한 조건과 필요한 자료를 적으세요.

      clauses는 실제 검색된 청크마다 하나씩, 제공된 순서대로 작성하세요. 같은 문서의 청크도 각각 담으세요.
      검색된 청크가 5개라면 항목도 5개입니다. 청크가 없으면 빈 배열입니다.
      quoteEn에는 이메일 작성자가 사용자 요청을 뒷받침하거나 적용 범위를 판단하는 데 직접 인용·참고할 구절만 담으세요.
      요청과 직접 관련된 정책 조항, 증빙 요건, 절차를 고르고 다른 상황의 안내나 일반 배경은 생략하세요.
      발췌문만 읽어도 적용 조건과 예외를 알 수 있도록 필요한 앞뒤 문장을 포함하세요.
      quoteEn은 해당 청크의 연속된 원문 구절 하나를 그대로 복사하세요. 번역하거나 구절을 이어 붙이지 마세요.
      직접 관련된 구절이 없으면 해당 항목의 quoteEn은 null, applicability는 NOT_APPLICABLE입니다.
      노트에는 근거를 요약하고 청크 원문 인용을 생략하세요.
      source는 제공된 메타데이터 그대로 쓰고 조항 제목·번호가 없으면 section은 null입니다.
      applicability는 적용 확인 시 APPLICABLE, 미확인 시 UNCERTAIN, 적용 불가 시 NOT_APPLICABLE입니다.
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
          "검색된 정책·안내문이 없습니다.",
          EvidenceStatus.NOT_ASSESSED,
          null,
          List.of(),
          List.of());
    }

    List<Double> similarityScores = new ArrayList<>();
    for (Document document : documents) {
      similarityScores.add(document.getScore());
    }

    PolicyRagModelResponse modelResponse = response.entity();
    return new PolicyRagResponse(
        modelResponse.getSearchStatus(),
        modelResponse.getSearchNote(),
        modelResponse.getEvidenceStatus(),
        modelResponse.getEvidenceNote(),
        modelResponse.getClauses(),
        similarityScores);
  }
}
