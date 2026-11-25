package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.config.policy.PolicySourceAdvisor;
import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.dto.response.PolicyRagResult.EvidenceStatus;
import com.teamrefund.gre.dto.response.PolicyRagResult.SearchStatus;
import java.util.List;
import java.util.Objects;
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
      검색된 공식 정책·안내문과 확인된 거래 사실을 대조해 유효한 JSON을 반환하세요.
      searchStatus는 요청을 해결하는 데 필요한 정책·안내 근거가 검색됐는지를 나타냅니다.
      - EVIDENCE_SUFFICIENT: 검색된 공식 문서가 해당 상황의 해결에 필요한 정책 조건 또는 절차를 충분히 설명합니다.
        거래 사실이나 증빙이 미확인이어도 필요한 정책 근거를 찾았다면 이 상태를 사용하세요.
      - EVIDENCE_INSUFFICIENT: 관련 문서를 찾았으나, 해결에 필요한 정책 조항이나 절차의 일부가 검색 결과에 빠져 있습니다.
      - POLICY_NOT_FOUND: 검색 결과에서 관련 정책을 찾지 못했습니다. clauses는 빈 배열입니다.
      clauses에는 소비자의 요청과 관련성이 높은 순서로 근거를 최대 %d개 반환하세요.
      quoteEn에는 소비자가 판매자에게 근거로 제시할 수 있는 정책 원문을 그대로 인용하세요.
      source에는 제공된 메타데이터 값을 그대로 사용하고 policyTitle에 정책 문서명을 넣으세요.
      요약에는 조항의 적용 조건과 예외를 보존하고, 원문이나 확인된 거래 사실에 없는 내용을 추가하지 마세요.
      조항 제목이나 번호가 없으면 section은 null입니다.
      적용이 확인되면 APPLICABLE, 미확인이면 UNCERTAIN, 적용 불가이면 NOT_APPLICABLE입니다.
      searchNote와 evidenceNote는 각각 별도의 한국어 문자열입니다. 각 항목을 한 줄씩 쓰세요.
      searchNote의 항목은 "검색된 근거:", "검색에서 빠진 근거:"입니다.
      해결에 필요한 정책 조건이나 절차를 얼마나 찾았는지 설명하고 빠진 내용이 있으면 구체적으로 적으세요.
      evidenceStatus는 검색된 문서의 요건에 비춰 현재까지 확인된 소비자의 거래 사실과 증빙을 판단합니다.
      - SUFFICIENT: 확인된 거래 사실과 증빙이 검색된 문서의 해당 조건을 충족하고 요청을 뒷받침합니다.
      - INSUFFICIENT: 문서가 요구하는 조건을 충족하지 못했거나 필요한 거래 사실 또는 증빙이 미확인입니다.
      - NOT_ASSESSED: 관련 정책이 없거나 증빙 요건을 판단할 정책 기준이 검색되지 않았습니다.
      evidenceNote의 항목은 "확인된 사실·증빙:", "추가로 준비할 증빙:"입니다.
      충족한 조건과 더 확인하거나 준비해야 할 자료를 검색된 문서에 근거해 구체적으로 설명하세요.
      확인되지 않은 증빙을 갖춘 것으로 간주하지 마세요. 추가 자료가 없거나 판단할 수 없으면 그 이유를 적으세요.
      정책 조항은 적용 조건의 근거로, 안내문에 나온 절차는 소비자가 취할 수 있는 조치로 설명하세요.
      원문과 거래 사실에서 확인되지 않은 환불이나 교환 결과를 보장하지 마세요.
      """;

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public PolicyRagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
    this.chatClient = chatClientBuilder.build();
    this.vectorStore = vectorStore;
  }

  public PolicyRagResult reviewPolicy(
      String requirement, double score, int topK, String platform) {
    return reviewWithDocuments(requirement, score, topK, platform).result();
  }

  PolicyReview reviewWithDocuments(
      String requirement, double score, int topK, String platform) {
    if (requirement == null || requirement.isBlank()) {
      PolicyRagResult result =
          new PolicyRagResult(
              SearchStatus.POLICY_NOT_FOUND,
              "검색 결과: 소비자 요청이 비어 있어 정책을 검색하지 않았습니다.\n필요한 근거: 소비자 요청을 확인하세요.",
              EvidenceStatus.NOT_ASSESSED,
              "확인된 사실·증빙: 증빙을 검토하지 못했습니다.\n추가로 준비할 증빙: 적용 요건을 확인할 정책 근거가 필요합니다.",
              List.of());
      return new PolicyReview(result, List.of());
    }

    FilterExpressionBuilder filters = new FilterExpressionBuilder();
    var platformFilter = filters.eq("platform", platform).build();

    SearchRequest searchRequest =
        SearchRequest.builder()
            .query(requirement)
            .filterExpression(platformFilter)
            .similarityThreshold(score)
            .topK(topK)
            .build();

    QuestionAnswerAdvisor policyAdvisor =
        QuestionAnswerAdvisor.builder(vectorStore).searchRequest(searchRequest).build();

    var response =
        chatClient
            .prompt()
            .advisors(policyAdvisor, new PolicySourceAdvisor())
            .system(REVIEW_PROMPT.formatted(topK))
            .user(requirement)
            .call()
            .responseEntity(PolicyRagResult.class);

    var chatResponse = Objects.requireNonNull(response.response(), "정책 RAG 모델 응답이 없습니다.");
    List<Document> documents =
        Objects.requireNonNull(
            chatResponse.getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS),
            "정책 RAG 검색 메타데이터가 없습니다.");
    if (documents.isEmpty()) {
      PolicyRagResult result =
          new PolicyRagResult(
              SearchStatus.POLICY_NOT_FOUND,
              "검색 결과: 검색된 정책 문서가 없습니다.\n필요한 근거: 요청을 뒷받침할 정책 조항이 필요합니다.",
              EvidenceStatus.NOT_ASSESSED,
              "확인된 사실·증빙: 증빙을 검토하지 못했습니다.\n추가로 준비할 증빙: 적용 요건을 확인할 정책 근거가 필요합니다.",
              List.of());
      return new PolicyReview(result, List.of());
    }
    PolicyRagResult result =
        Objects.requireNonNull(response.entity(), "구조화된 정책 RAG 결과가 없습니다.");
    if (result.clauses() != null && result.clauses().size() > topK) {
      result =
          new PolicyRagResult(
              result.searchStatus(),
              result.searchNote(),
              result.evidenceStatus(),
              result.evidenceNote(),
              result.clauses().subList(0, topK));
    }
    return new PolicyReview(result, documents);
  }

  record PolicyReview(PolicyRagResult result, List<Document> documents) {}
}
