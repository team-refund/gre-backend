package com.teamrefund.gre.tool;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.document.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PolicySearchTool {
  public static final String DOCUMENTS = "policyDocuments";
  public static final String QUERIES = "policyQueries";
  public static final String PLATFORM = "policyPlatform";
  private static final int MAX_SEARCHES = 3;

  private final VectorStore vectorStore;
  private final double similarityThreshold;
  private final int topK;

  public PolicySearchTool(
      VectorStore vectorStore,
      @Value("${gre.policy.search.similarity-threshold}") double similarityThreshold,
      @Value("${gre.policy.search.top-k}") int topK) {
    this.vectorStore = vectorStore;
    this.similarityThreshold = similarityThreshold;
    this.topK = topK;
  }

  @Tool(description = "공식 정책을 벡터 검색합니다. 정책 요건을 찾을 검색어를 입력하세요. 최대 3회 호출할 수 있습니다.")
  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> searchPolicy(String query, ToolContext context) {
    if (!StringUtils.hasText(query)) {
      throw new BusinessException(ErrorCode.POLICY_SEARCH_FAILED);
    }
    List<String> queries = (List<String>) context.getContext().get(QUERIES);
    if (queries.size() >= MAX_SEARCHES) {
      throw new BusinessException(ErrorCode.POLICY_SEARCH_FAILED);
    }
    queries.add(query);

    SearchRequest searchRequest =
        SearchRequest.builder()
            .query(query)
            .filterExpression(
                new FilterExpressionBuilder()
                    .eq("platform", context.getContext().get(PLATFORM))
                    .build())
            .similarityThreshold(similarityThreshold)
            .topK(topK)
            .build();
    List<Document> documents = vectorStore.similaritySearch(searchRequest);
    List<Document> retrieved = (List<Document>) context.getContext().get(DOCUMENTS);
    for (Document document : documents) {
      if (retrieved.stream().noneMatch(existing -> existing.getId().equals(document.getId()))) {
        retrieved.add(document);
      }
    }
    return documents.stream()
        .map(
            document ->
                Map.<String, Object>of(
                    "chunkId", document.getId(),
                    "text", document.getText(),
                    "metadata", document.getMetadata()))
        .toList();
  }
}
