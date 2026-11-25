package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.dto.response.PolicyRagEvaluationResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// 실제 DB와 모델을 호출하므로 명시적으로 켰을 때만 실행한다.
@EnabledIfEnvironmentVariable(named = "GRE_RAG_EVALUATION_ENABLED", matches = "true")
@SpringBootTest(properties = "gre.policy.ingestion.ebay-enabled=false")
@Slf4j
class PolicyRagEvaluationTest {

  private static final String QUESTION =
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

  @Autowired PolicyRagTestService policyRagTestService;

  @Test
  void retrievesRefundClausesForAnUndeliveredEbayOrder() throws IOException {
    int topK = Integer.parseInt(System.getenv().getOrDefault("GRE_RAG_EVALUATION_TOP_K", "5"));
    double threshold =
        Double.parseDouble(System.getenv().getOrDefault("GRE_RAG_EVALUATION_THRESHOLD", "0.0"));

    PolicyRagEvaluationResponse evaluation =
        policyRagTestService.evaluate(QUESTION, threshold, topK, "EBAY");
    saveReport(topK, threshold, evaluation);
  }

  private void saveReport(int topK, double threshold, PolicyRagEvaluationResponse evaluation)
      throws IOException {
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
                evaluation.retrievedDocumentCount(),
                evaluation.relevancyPassed() ? 1 : 0,
                evaluation.factCheckingPassed() ? 1 : 0);
    Files.writeString(reportDirectory.resolve("evaluation.md"), report);
    Files.writeString(reportDirectory.resolve("evaluation.log"), report);
    log.info("\n{}", report);
  }
}
