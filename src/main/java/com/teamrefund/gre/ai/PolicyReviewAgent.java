package com.teamrefund.gre.ai;

import com.teamrefund.gre.dao.policy.PolicyRagResultDao;
import com.teamrefund.gre.dto.response.policy.PolicyRagModelResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Clause;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.Review;
import com.teamrefund.gre.dto.response.policy.PolicyRagResponse.SearchStatus;
import com.teamrefund.gre.entity.policy.PolicyRagResult;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.tool.PolicySearchTool;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class PolicyReviewAgent {
  private static final String REVIEW_PROMPT =
      """
      반드시 searchPolicy 도구를 호출해 공식 정책을 검색한 후 JSON을 작성하세요. 필요하면 검색어를 바꾸어 최대 3회 검색하세요. 도구 결과의 모든 고유 chunkId를 검토하세요. 검색된 공식 정책과 확인된 거래 사실만으로 JSON을 작성하세요. 설명은 한국어로 작성하고 정책의 요건과 예외를 반영하세요.

      입력은 사건정리 AI가 구조화한 사건 정보입니다.
      검색어에는 정책 판단에 영향을 주는 핵심 맥락을 구체적으로 반영하세요.
      사건 유형과 분쟁 사유, 주문·결제·배송·취소·반품·환불의 현재 상태,
      주요 사건의 순서와 경과 기간, 판매자의 약속·응답·거절 사유,
      소비자의 요구사항 및 관련 상품·거래 조건 중 입력에 있는 관련 정보를 포함하세요.
      단순히 "환불 정책"처럼 넓은 주제로 축약하지 말고, 해당 상황의 요건·기한·예외·처리 절차를 묻는 검색어를 작성하세요.
      날짜는 정책 판단에 필요한 경우 반영하고, 경과 기간은 입력에서 확인할 수 있을 때만 사용하세요.
      이름·이메일·주문번호처럼 정책 검색에 도움이 되지 않는 식별정보는 검색어에서 제외하세요.
      확인된 사실과 미확인 사항을 구분하고, 미확인 조건을 충족한 것으로 검색어에 넣지 마세요.
      사건정리 AI가 검색어를 제공했다면 이를 우선 사용하고, 핵심 맥락이 빠졌을 때만 입력에 근거해 보완하세요.
      쟁점이 여러 개라면 첫 검색에 핵심 상황을 반영하고, 추가 검색은 누락된 쟁점을 구체적으로 다루세요.
      예를 들어 입력에서 반품 배송 완료 후 10일 경과와 판매자의 환불 약속이 확인된다면,
      "반품 상품의 판매자 수령 완료 후 10일 동안 약속한 환불이 처리되지 않은 경우 판매자의 환불 기한과 구매자 보호 신청 요건·절차"처럼 작성하세요.
      예시의 사실을 실제 사건에 추가하지 마세요.
      불확실하거나 누락된 항목은 확인된 사실로 간주하지 마세요.

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
  private final PolicySearchTool policySearchTool;
  private final PolicyRagResultDao policyRagResultDao;
  private final ObjectMapper objectMapper;

  public PolicyReviewAgent(
      ChatClient.Builder chatClientBuilder,
      PolicySearchTool policySearchTool,
      PolicyRagResultDao policyRagResultDao,
      ObjectMapper objectMapper) {
    this.chatClient = chatClientBuilder.build();
    this.policySearchTool = policySearchTool;
    this.policyRagResultDao = policyRagResultDao;
    this.objectMapper = objectMapper;
  }

  // 상위 실행 계층에서 호출하며, 저장이 완료된 결과 ID를 반환한다.
  public Long reviewAndSave(String caseAnalysis, String platform) {
    PolicyRagResponse response = reviewPolicy(caseAnalysis, platform);
    PolicyRagResult result = new PolicyRagResult();
    result.setClauses(objectMapper.writeValueAsString(response.getClauses()));
    result.setReviews(objectMapper.writeValueAsString(response.getReviews()));
    result.setEvidenceGaps(response.getEvidenceGaps());
    if (policyRagResultDao.insert(result) != 1 || result.getId() == null) {
      throw new BusinessException(ErrorCode.POLICY_RESULT_SAVE_FAILED);
    }
    return result.getId();
  }

  public PolicyRagResponse reviewPolicy(String caseAnalysis, String platform) {
    List<Document> documents = new ArrayList<>();
    List<String> queries = new ArrayList<>();
    var response =
        chatClient
            .prompt()
            .tools(policySearchTool)
            .toolContext(
                Map.of(
                    PolicySearchTool.DOCUMENTS, documents,
                    PolicySearchTool.QUERIES, queries,
                    PolicySearchTool.PLATFORM, platform))
            .system(REVIEW_PROMPT)
            .user("사건정리 AI의 구조화 결과:\n" + caseAnalysis)
            .call()
            .responseEntity(PolicyRagModelResponse.class);
    if (queries.isEmpty()) {
      throw new BusinessException(ErrorCode.POLICY_REVIEW_FAILED);
    }
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
      clause.setOriginalTextEn(document.getText());
      clause.setOfficialUrl((String) metadata.get("sourceUrl"));
      clauses.add(clause);
    }

    PolicyRagModelResponse modelResponse = response.entity();
    if (modelResponse == null
        || modelResponse.getSearchStatus() == null
        || modelResponse.getReviews() == null
        || modelResponse.getReviews().size() != documents.size()) {
      throw new BusinessException(ErrorCode.POLICY_REVIEW_FAILED);
    }

    List<Review> reviews = modelResponse.getReviews();
    // 검색 조항을 한 번씩 검토했는지 확인한다. 제거에 실패하면 중복 또는 미검색 조항이다.
    for (Review review : reviews) {
      if (review == null
          || review.getApplicability() == null
          || !retrievedIds.remove(review.getChunkId())) {
        throw new BusinessException(ErrorCode.POLICY_REVIEW_FAILED);
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
