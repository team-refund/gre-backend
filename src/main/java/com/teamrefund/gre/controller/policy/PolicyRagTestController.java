package com.teamrefund.gre.controller.policy;

import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.service.policy.PolicyRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class PolicyRagTestController {

  public static final String PATH = "/internal/test/policy-rag";

  private final PolicyRagService policyRagService;

  @GetMapping(PATH)
  public PolicyRagResult reviewPolicy(
      @RequestParam("question") String question,
      @RequestParam(value = "topK", defaultValue = "5") int topK,
      @RequestParam(value = "similarityThreshold", defaultValue = "0.0")
          double similarityThreshold) {
    if (topK < 1
        || !Double.isFinite(similarityThreshold)
        || similarityThreshold < 0
        || similarityThreshold > 1) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "topK는 1 이상, similarityThreshold는 0~1이어야 합니다.");
    }
    return policyRagService.reviewPolicy(question, similarityThreshold, "EBAY", topK);
  }

  @PostMapping(value = PATH + "/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public String ingestPolicy(
      @RequestParam("file") MultipartFile file,
      @RequestParam("policyTitle") String policyTitle,
      @RequestParam("platform") String platform,
      @RequestParam("site") String site,
      @RequestParam("sourceUrl") String sourceUrl,
      @RequestParam(value = "selector", defaultValue = ".article_body > .article_main_container")
          String selector,
      @RequestParam(value = "chunkSize", defaultValue = "800") int chunkSize,
      @RequestParam(value = "minChunkSizeChars", defaultValue = "350") int minChunkSizeChars) {
    if (chunkSize < 1 || minChunkSizeChars < 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "chunkSize는 1 이상, minChunkSizeChars는 0 이상이어야 합니다.");
    }
    if (file.isEmpty()
        || policyTitle.isBlank()
        || platform.isBlank()
        || site.isBlank()
        || sourceUrl.isBlank()
        || selector.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "HTML 파일과 정책 정보를 입력하세요.");
    }
    return policyRagService.ingestPolicyFromHtml(
        policyTitle,
        platform,
        site,
        file.getResource(),
        sourceUrl,
        selector,
        chunkSize,
        minChunkSizeChars);
  }
}
