package com.teamrefund.gre.controller.policy;

import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_CHUNK_SIZE;
import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_MIN_CHUNK_SIZE_CHARS;
import static com.teamrefund.gre.service.policy.PolicyIngestionService.DEFAULT_SELECTOR;

import com.teamrefund.gre.dto.response.policy.PolicyRagResponse;
import com.teamrefund.gre.service.policy.PolicyIngestionService;
import com.teamrefund.gre.service.policy.PolicyRagService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
@RequestMapping("/rag")
@RequiredArgsConstructor
public class PolicyRagTestController {
  private final PolicyRagService policyRagService;
  private final PolicyIngestionService policyIngestionService;

  @GetMapping
  public PolicyRagResponse reviewPolicy(
      @RequestParam(name = "question") String question,
      @RequestParam(name = "platform", defaultValue = "EBAY") String platform) {
    return policyRagService.reviewPolicy(question, platform);
  }

  @PostMapping(value = "/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> ingestPolicy(
      @RequestParam(name = "file") MultipartFile file,
      @RequestParam(name = "policyTitle") @NotBlank String policyTitle,
      @RequestParam(name = "platform") @NotBlank String platform,
      @RequestParam(name = "site") @NotBlank String site,
      @RequestParam(name = "sourceUrl") @NotBlank String sourceUrl,
      @RequestParam(name = "selector", defaultValue = DEFAULT_SELECTOR) @NotBlank String selector,
      @RequestParam(name = "chunkSize", defaultValue = "" + DEFAULT_CHUNK_SIZE) @Positive
          int chunkSize,
      @RequestParam(name = "minChunkSizeChars", defaultValue = "" + DEFAULT_MIN_CHUNK_SIZE_CHARS)
          @PositiveOrZero
          int minChunkSizeChars) {
    if (file.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "HTML 파일을 입력하세요.");
    }

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
}
