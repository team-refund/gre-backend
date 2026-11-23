package com.teamrefund.gre.service.policy;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.jsoup.JsoupDocumentReader;
import org.springframework.ai.reader.jsoup.config.JsoupDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PolicyIngestionService {
  public static final String DEFAULT_SELECTOR = ".article_body > .article_main_container";
  public static final int DEFAULT_CHUNK_SIZE = 800;
  public static final int DEFAULT_MIN_CHUNK_SIZE_CHARS = 350;

  private final VectorStore vectorStore;

  public String ingestPolicyFromHtml(
      String policyTitle,
      String platform,
      String site,
      Resource html,
      String sourceUrl,
      String selector,
      int chunkSize,
      int minChunkSizeChars) {
    // 저장한 HTML을 읽고, 공식 URL은 정책 인용 출처로 보존한다.
    JsoupDocumentReader reader =
        new JsoupDocumentReader(
            html, JsoupDocumentReaderConfig.builder().charset("UTF-8").selector(selector).build());

    List<Document> documents = reader.read();
    log.info("추출된 Document 수: {} 개", documents.size());

    // Spring AI 2.x에서는 사용 중단된 생성자 대신 기본값이 같은 빌더를 사용한다.
    DocumentTransformer transformer =
        TokenTextSplitter.builder()
            .withChunkSize(chunkSize)
            .withMinChunkSizeChars(minChunkSizeChars)
            .build();

    log.info("청킹 설정: chunkSize={}, minChunkSizeChars={}", chunkSize, minChunkSizeChars);

    List<Document> transformedDocuments = transformer.apply(documents);
    if (transformedDocuments.isEmpty()) {
      throw new IllegalArgumentException("HTML에서 정책 본문을 추출하지 못했습니다. selector를 확인하세요.");
    }
    log.info("변환된 Document 수: {} 개", transformedDocuments.size());
    // 청크마다 GRE의 사이트 검색과 정책 인용에 필요한 메타데이터를 붙인다.
    Map<String, Object> metadata =
        Map.of(
            "platform", platform,
            "site", site,
            "sourceUrl", sourceUrl,
            "policyTitle", policyTitle);
    for (Document document : transformedDocuments) {
      document.getMetadata().putAll(metadata);
    }
    vectorStore.add(transformedDocuments);

    return transformedDocuments.size() + "개 정책 청크를 적재했습니다.";
  }
}
