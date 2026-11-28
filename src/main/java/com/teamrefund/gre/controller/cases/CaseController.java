package com.teamrefund.gre.controller.cases;

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
public class CaseController {
  // 새 환불 사건을 등록합니다.
  @PostMapping
  public ResponseEntity<Void> createCase(@RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 로그인한 사용자의 사건 목록을 조회합니다.
  @GetMapping
  public ResponseEntity<Void> listCases(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "20") Integer size,
      @RequestParam(name = "status", required = false) String status) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 사건 상세 정보를 조회합니다.
  @GetMapping("/{caseId}")
  public ResponseEntity<Void> getCase(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
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
  public ResponseEntity<Void> listEvidences(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 증빙 파일과 메타데이터를 조회합니다.
  @GetMapping("/{caseId}/evidences/{evidenceId}")
  public ResponseEntity<Void> getEvidence(
      @PathVariable("caseId") Long caseId, @PathVariable("evidenceId") Long evidenceId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
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

  // 사건 분석 또는 재판단을 비동기로 요청합니다.
  @PostMapping("/{caseId}/runs")
  public ResponseEntity<Void> startRun(
      @PathVariable("caseId") Long caseId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
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
