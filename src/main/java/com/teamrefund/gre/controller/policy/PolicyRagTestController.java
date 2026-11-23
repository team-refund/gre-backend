package com.teamrefund.gre.controller.policy;

import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_CHUNK_SIZE;
import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_MIN_CHUNK_SIZE_CHARS;
import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_SELECTOR;

import com.teamrefund.gre.dto.response.PolicyRagResult;
import com.teamrefund.gre.service.policy.PolicyIngestionService;
import com.teamrefund.gre.service.policy.PolicyRagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(PolicyRagTestController.PATH)
@RequiredArgsConstructor
public class PolicyRagTestController {
  public static final String PATH = "/internal/test/policy-rag";

  private final PolicyRagService policyRagService;
  private final PolicyIngestionService policyIngestionService;

  @GetMapping("")
  public PolicyRagResult reviewPolicy(
      @RequestParam(name = "question") String question,
      @RequestParam(name = "platform", defaultValue = "EBAY") String platform,
      @RequestParam(name = "site", defaultValue = "ebay.com") String site,
      @RequestParam(name = "topK", defaultValue = "5") int topK,
      @RequestParam(name = "similarityThreshold", defaultValue = "0.0")
          double similarityThreshold) {
    validateReviewParameters(topK, similarityThreshold);
    return policyRagService.reviewPolicy(question, similarityThreshold, topK, platform, site);
  }

  @PostMapping(value = "/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> ingestPolicy(
      @RequestParam(name = "file") MultipartFile file,
      @RequestParam(name = "policyTitle") String policyTitle,
      @RequestParam(name = "platform") String platform,
      @RequestParam(name = "site") String site,
      @RequestParam(name = "sourceUrl") String sourceUrl,
      @RequestParam(name = "selector", defaultValue = DEFAULT_SELECTOR) String selector,
      @RequestParam(name = "chunkSize", defaultValue = "" + DEFAULT_CHUNK_SIZE) int chunkSize,
      @RequestParam(name = "minChunkSizeChars", defaultValue = "" + DEFAULT_MIN_CHUNK_SIZE_CHARS)
          int minChunkSizeChars) {
    validateChunkSizes(chunkSize, minChunkSizeChars);
    validatePolicyInput(file, policyTitle, platform, site, sourceUrl, selector);

    String result =
        policyIngestionService.ingestPolicyFromHtml(
            policyTitle,
            platform,
            site,
            file.getResource(),
            sourceUrl,
            selector,
            chunkSize,
            minChunkSizeChars);
    return ResponseEntity.ok(result);
  }

  private void validateReviewParameters(int topK, double similarityThreshold) {
    if (topK < 1
        || !Double.isFinite(similarityThreshold)
        || similarityThreshold < 0
        || similarityThreshold > 1) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "topK는 1 이상, similarityThreshold는 0~1이어야 합니다.");
    }
  }

  private void validateChunkSizes(int chunkSize, int minChunkSizeChars) {
    if (chunkSize < 1 || minChunkSizeChars < 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "chunkSize는 1 이상, minChunkSizeChars는 0 이상이어야 합니다.");
    }
  }

  private void validatePolicyInput(
      MultipartFile file,
      String policyTitle,
      String platform,
      String site,
      String sourceUrl,
      String selector) {
    if (file.isEmpty()
        || policyTitle.isBlank()
        || platform.isBlank()
        || site.isBlank()
        || sourceUrl.isBlank()
        || selector.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "HTML 파일과 정책 정보를 입력하세요.");
    }
  }
}
