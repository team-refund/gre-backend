package com.teamrefund.gre.service.policy;

import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.dto.response.PolicyRagResult.Status;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.jsoup.JsoupDocumentReader;
import org.springframework.ai.reader.jsoup.config.JsoupDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolicyRagService {

  private final ChatClient chatClient;
  private final VectorStore vectorStore;

  public PolicyRagService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {

    this.chatClient = chatClientBuilder.build();
    this.vectorStore = vectorStore;
  }

  public String ingestPolicyFromHtml(
      String policyTitle, String platform, String site, Resource html, String sourceUrl) {
    // 저장한 HTML을 읽고, 공식 URL은 정책 인용 출처로 보존한다.
    JsoupDocumentReader reader =
        new JsoupDocumentReader(
            html,
            JsoupDocumentReaderConfig.builder()
                .charset("UTF-8")
                .selector(".article_body > .article_main_container")
                .build());

    List<Document> documents = reader.read();
    log.info("추출된 Document 수: {} 개", documents.size());

    // Spring AI 2.x에서는 사용 중단된 생성자 대신 기본값이 같은 빌더를 사용한다.
    DocumentTransformer transformer = TokenTextSplitter.builder().build();
    List<Document> transformedDocuments = transformer.apply(documents);
    log.info("변환된 Document 수: {} 개", transformedDocuments.size());
    // 청크마다 GRE의 사이트 검색과 정책 인용에 필요한 메타데이터를 붙인다.
    Map<String, Object> metadata =
        Map.of(
            "platform", platform,
            "site", site,
            "sourceUrl", sourceUrl,
            "policyTitle", policyTitle,
            "updatedAt", Instant.now().toString());
    List<Document> chunks = new ArrayList<>(transformedDocuments.size());
    for (Document document : transformedDocuments) {
      chunks.add(new Document(document.getText(), metadata));
    }
    vectorStore.add(chunks);

    return "HTML에서 추출-변환-적재 완료 했습니다.";
  }

  public PolicyRagResult reviewPolicy(String requirement, double score, String source) {
    if (requirement == null || requirement.isBlank() || !"EBAY".equals(source)) {
      return new PolicyRagResult(Status.INSUFFICIENT_FACTS, List.of(), "소비자 요구와 결제 사이트를 확인하세요.");
    }

    SearchRequest searchRequest =
        SearchRequest.builder()
            .query(requirement)
            .filterExpression("platform == 'EBAY' && site == 'ebay.com'")
            .similarityThreshold(score)
            .topK(5)
            .build();
    // 검색 결과가 없으면 모델을 호출하기 전에 반환하고, 결과가 있으면 본문과 출처를 함께 전달한다.
    List<Document> documents = vectorStore.similaritySearch(searchRequest);
    if (documents.isEmpty()) {
      return new PolicyRagResult(Status.NOT_FOUND, List.of(), "관련 공식 정책 조항이 없습니다.");
    }

    StringJoiner context = new StringJoiner("\n\n");
    for (Document document : documents) {
      context.add(document.getMetadata() + "\n" + document.getText());
    }

    return chatClient
        .prompt()
        .system(
            """
            제공된 공식 정책과 확인된 거래 사실을 대조해 유효한 JSON을 반환하세요.
            관련 조항이 있으면 status는 COMPLETED, 없으면 NOT_FOUND와 빈 clauses 배열을 반환하세요.
            quoteEn과 source는 제공된 원문과 메타데이터의 값을 그대로 사용하세요.
            조항 제목이나 번호가 없으면 section은 null입니다.
            적용이 확인되면 APPLICABLE, 미확인이면 UNCERTAIN, 적용 불가이면 NOT_APPLICABLE입니다.
            note에는 근거 부족 이유나 확인할 사항을 적고, 없으면 null로 두세요.
            """)
        .user("소비자 요구와 사건 정보:\n" + requirement + "\n\n공식 정책:\n" + context)
        .call()
        .entity(PolicyRagResult.class);
  }
}
