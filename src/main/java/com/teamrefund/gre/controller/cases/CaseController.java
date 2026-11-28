package com.teamrefund.gre.controller.cases;

import com.teamrefund.gre.dto.request.cases.CaseRegistrationRequest;
import com.teamrefund.gre.dto.request.cases.CaseRequirementUpdateRequest;
import com.teamrefund.gre.dto.response.cases.CaseDetailResponse;
import com.teamrefund.gre.dto.response.cases.CaseDetailResponse.EmailDraftSummary;
import com.teamrefund.gre.dto.response.cases.CaseEvidenceResponse;
import com.teamrefund.gre.dto.response.cases.CaseListResponse;
import com.teamrefund.gre.dto.response.cases.CaseListResponse.CaseSummary;
import com.teamrefund.gre.dto.response.cases.CaseRegistrationResponse;
import com.teamrefund.gre.dto.response.cases.CaseRunAcceptedResponse;
import com.teamrefund.gre.dto.response.orchestrator.OrchestratorRunResponse;
import com.teamrefund.gre.entity.cases.Case;
import com.teamrefund.gre.service.cases.CaseRegistrationService;
import com.teamrefund.gre.service.cases.CaseRequirementService;
import com.teamrefund.gre.service.cases.CaseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {
  private final CaseService caseService;
  private final CaseRegistrationService caseRegistrationService;
  private final CaseRequirementService caseRequirementService;

  // 새 환불 사건을 등록합니다.
  @PostMapping
  public ResponseEntity<CaseRegistrationResponse> createCase(
      Principal principal, @Valid @RequestBody CaseRegistrationRequest request) {
    var result = caseRegistrationService.registerCase(principal.getName(), request);
    return ResponseEntity.accepted()
        .location(URI.create("/api/cases/" + result.getCaseId()))
        .body(new CaseRegistrationResponse(result.getCaseId(), result.getRunId()));
  }

  // 로그인한 사용자의 사건 목록을 조회합니다.
  @GetMapping
  public CaseListResponse listCases(
      Principal principal,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size,
      @RequestParam(name = "status", required = false) String status) {
    var result =
        caseService.listCases(principal == null ? null : principal.getName(), page, size, status);
    var cases = result.getCases().stream().map(this::toCaseSummary).toList();
    return new CaseListResponse(cases, page, size, result.getTotalElements());
  }

  @GetMapping("/{caseId}")
  public CaseDetailResponse getCase(Principal principal, @PathVariable("caseId") long caseId) {
    var detail = caseService.getCaseDetail(principal == null ? null : principal.getName(), caseId);
    var item = detail.getRefundCase();
    var latestRun = detail.getLatestRun();
    var drafts =
        detail.getEmailDrafts().stream()
            .map(
                draft ->
                    new EmailDraftSummary(
                        draft.getId(), draft.getSubject(), draft.getStatus(), draft.getUpdatedAt()))
            .toList();
    return new CaseDetailResponse(
        item.getId(),
        item.getTitle(),
        item.getCaseType(),
        item.getCasesStatus(),
        item.getMerchantName(),
        item.getMerchantEmail(),
        item.getPurchaseSite(),
        item.getOrderNumber(),
        item.getPurchaseDate(),
        item.getPaymentAmount(),
        item.getCreatedAt(),
        item.getUpdatedAt(),
        item.getResolvedAt(),
        detail.getRequirement(),
        latestRun == null ? null : new OrchestratorRunResponse(latestRun),
        drafts,
        detail.getEvidences().stream().map(CaseEvidenceResponse::new).toList());
  }

  private CaseSummary toCaseSummary(Case item) {
    return new CaseSummary(
        item.getId(),
        item.getTitle(),
        item.getCaseType(),
        item.getCasesStatus(),
        item.getMerchantName(),
        item.getCreatedAt(),
        item.getUpdatedAt());
  }

  // 사건 상태 변경 이력을 조회합니다.
  @GetMapping("/{caseId}/histories")
  public ResponseEntity<Void> listHistories(
      @PathVariable("caseId") Long caseId,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사진 증빙을 사건에 연결하고 저장 후 분석을 시작합니다.
  @PostMapping(path = "/{caseId}/evidences", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadEvidence(
      @PathVariable("caseId") Long caseId,
      @RequestPart("file") MultipartFile file,
      @RequestPart(name = "metadata", required = false) String metadata) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사건에 연결된 증빙 목록을 조회합니다.
  @GetMapping("/{caseId}/evidences")
  public List<CaseEvidenceResponse> listEvidences(
      Principal principal, @PathVariable("caseId") long caseId) {
    return caseService.listEvidences(principal.getName(), caseId).stream()
        .map(CaseEvidenceResponse::new)
        .toList();
  }

  // 증빙 파일의 메타데이터를 조회합니다.
  @GetMapping("/{caseId}/evidences/{evidenceId}")
  public CaseEvidenceResponse getEvidence(
      Principal principal,
      @PathVariable("caseId") long caseId,
      @PathVariable("evidenceId") long evidenceId) {
    return new CaseEvidenceResponse(
        caseService.getEvidence(principal.getName(), caseId, evidenceId));
  }

  // 사건에서 사용자 응답이 필요한 요청을 조회합니다.
  @GetMapping("/{caseId}/interactions")
  public ResponseEntity<Void> listInteractions(
      @PathVariable("caseId") Long caseId,
      @RequestParam(name = "status", required = false) String status,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사용자 응답과 선택한 증빙을 제출합니다.
  @PostMapping("/{caseId}/interactions/{interactionId}/answer")
  public ResponseEntity<Void> answerInteraction(
      @PathVariable("caseId") Long caseId,
      @PathVariable("interactionId") Long interactionId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/runs")
  public ResponseEntity<CaseRunAcceptedResponse> submitRequirementUpdate(
      Principal principal,
      @PathVariable("caseId") long caseId,
      @Valid @RequestBody CaseRequirementUpdateRequest request) {
    var run =
        caseRequirementService.submitRequirementUpdate(
            principal.getName(), caseId, request.getRequestKey(), request.getRequirement());
    return ResponseEntity.accepted()
        .body(new CaseRunAcceptedResponse(run.getId(), run.getOrchestratorRunsStatus()));
  }

  // 사건의 AI 실행 이력을 조회합니다.
  @GetMapping("/{caseId}/runs")
  public ResponseEntity<Void> listRuns(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사건의 정책 근거와 검토 결과를 조회합니다.
  @GetMapping("/{caseId}/policy-evidences")
  public ResponseEntity<Void> listPolicyEvidences(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 판매자 회신 분석과 사건 재판단을 요청합니다.
  @PostMapping("/{caseId}/replies/analyze")
  public ResponseEntity<Void> analyzeReply(
      @PathVariable("caseId") Long caseId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사용자가 확인한 환불 결과를 제출합니다.
  @PostMapping("/{caseId}/refund-confirmation")
  public ResponseEntity<Void> confirmRefund(
      @PathVariable("caseId") Long caseId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 환불 확인 이후 사건을 종료합니다.
  @PostMapping("/{caseId}/close")
  public ResponseEntity<Void> closeCase(
      @PathVariable("caseId") Long caseId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
