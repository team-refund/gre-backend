package com.teamrefund.gre.service.policy;

import static org.assertj.core.api.Assertions.assertThat;

import com.teamrefund.gre.dto.response.PolicyRagResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.chat.evaluation.RelevancyEvaluator;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.json.JsonMapper;

// 실제 DB와 모델을 호출하므로 명시적으로 켰을 때만 실행한다.
@EnabledIfEnvironmentVariable(named = "GRE_RAG_EVALUATION_ENABLED", matches = "true")
@SpringBootTest(properties = "gre.policy.ingestion.ebay-enabled=false")
@Slf4j
class PolicyRagEvaluationTest {

  @Autowired PolicyRagService policyRagService;
  @Autowired ChatClient.Builder chatClientBuilder;
  @Autowired JsonMapper jsonMapper;

  @Test
  void retrievesRefundClausesForAnUndeliveredEbayOrder() throws Exception {
    String question =
        """
        ebay.com에서 개인 용도로 무선 헤드폰을 120달러에 구매했고 전액 결제했습니다.
        테스트 사건의 오늘 날짜는 2026년 11월 23일입니다.
        구매일은 2026년 11월 5일, 예상 배송일의 마지막 날은 11월 18일입니다.
        아직 물건을 받지 못했고 배송 추적에도 배송 완료 표시가 없습니다.
        11월 19일 eBay에서 미수령 신고와 환불 요청을 했으나 판매자는 응답하지 않았습니다.
        판매자에게 환불을 요청하는 이메일의 근거로 사용할 공식 정책 조항을 찾아주세요.
        미수령 보장 범위, 신고 기한, 판매자 응답 기한과 eBay 개입 요청 조건을 확인해주세요.
        확인되지 않은 사실은 추정하지 말고 정책 적용이 불확실하면 표시해주세요.
        """;
    int topK = Integer.parseInt(System.getenv().getOrDefault("GRE_RAG_EVALUATION_TOP_K", "5"));
    double threshold =
        Double.parseDouble(System.getenv().getOrDefault("GRE_RAG_EVALUATION_THRESHOLD", "0.0"));

    ResponseEntity<ChatResponse, PolicyRagResult> response =
        policyRagService.reviewPolicyWithResponse(question, threshold, topK);
    PolicyRagResult result = response.getEntity();
    List<Document> documents =
        response.getResponse().getMetadata().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
    assertThat(documents).isNotEmpty();
    assertThat(result).isNotNull();
    String answer = jsonMapper.writeValueAsString(result);
    List<Document> evidence = new ArrayList<>();
    for (Document document : documents) {
      evidence.add(new Document(document.getMetadata() + "\n" + document.getText()));
    }
    EvaluationRequest evaluationRequest = new EvaluationRequest(question, evidence, answer);
    EvaluationResponse relevancy =
        new RelevancyEvaluator(chatClientBuilder).evaluate(evaluationRequest);
    EvaluationResponse factChecking =
        FactCheckingEvaluator.builder(chatClientBuilder).build().evaluate(evaluationRequest);

    saveReport(topK, threshold, documents.size(), relevancy, factChecking);
    verifyClauses(result, documents);
    assertThat(relevancy.isPass()).as("관련성 평가").isTrue();
    assertThat(factChecking.isPass()).as("사실 근거 평가").isTrue();
  }

  private void verifyClauses(PolicyRagResult result, List<Document> documents) {
    assertThat(result.status()).isEqualTo(PolicyRagResult.Status.COMPLETED);
    assertThat(result.clauses()).isNotEmpty();

    // 이메일의 근거가 될 인용문과 출처가 같은 검색 문서에 존재하는지 확인한다.
    for (PolicyRagResult.Clause clause : result.clauses()) {
      assertThat(clause.quoteEn()).isNotBlank();
      assertThat(clause.source()).isNotNull();
      assertThat(clause.source().platform()).isEqualTo("EBAY");
      assertThat(clause.source().site()).isEqualTo("ebay.com");
      boolean found = false;
      for (Document document : documents) {
        if (normalize(document.getText()).contains(normalize(clause.quoteEn()))
            && clause.source().sourceUrl().equals(document.getMetadata().get("sourceUrl"))
            && clause.source().policyTitle().equals(document.getMetadata().get("policyTitle"))) {
          found = true;
          break;
        }
      }
      assertThat(found).as("검색 문서에 인용문과 출처가 있어야 합니다.").isTrue();
    }
  }

  private void saveReport(
      int topK,
      double threshold,
      int documentCount,
      EvaluationResponse relevancy,
      EvaluationResponse factChecking)
      throws Exception {
    Path reportDirectory =
        Path.of("build", "reports", "policy-rag", Instant.now().toString().replace(':', '-'));
    Files.createDirectories(reportDirectory);
    String report =
        """
        # RAG 평가 결과

        | topK | 최소 유사도 | 검색 청크 수 |
        |---:|---:|---:|
        | %d | %s | %d |

        | 평가 | 통과값 (통과 1, 실패 0) |
        |---|---:|
        | 관련성 | %d |
        | 사실 근거 | %d |
        """
            .formatted(
                topK,
                threshold,
                documentCount,
                relevancy.isPass() ? 1 : 0,
                factChecking.isPass() ? 1 : 0);
    Files.writeString(reportDirectory.resolve("evaluation.md"), report);
    Files.writeString(reportDirectory.resolve("evaluation.log"), report);
    log.info("\n{}", report);
  }

  private String normalize(String text) {
    return text.replaceAll("\\s+", " ").strip();
  }
}
