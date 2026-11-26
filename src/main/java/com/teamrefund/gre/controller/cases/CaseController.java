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
  @PostMapping
  public ResponseEntity<Void> createCase(@RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping
  public ResponseEntity<Void> listCases(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "20") Integer size,
      @RequestParam(name = "status", required = false) String status) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}")
  public ResponseEntity<Void> getCase(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/histories")
  public ResponseEntity<Void> listHistories(
      @PathVariable("caseId") Long caseId,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping(path = "/{caseId}/evidences", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadEvidence(
      @PathVariable("caseId") Long caseId,
      @RequestPart("file") MultipartFile file,
      @RequestPart(name = "metadata", required = false) String metadata) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/evidences")
  public ResponseEntity<Void> listEvidences(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/evidences/{evidenceId}")
  public ResponseEntity<Void> getEvidence(
      @PathVariable("caseId") Long caseId, @PathVariable("evidenceId") Long evidenceId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/interactions")
  public ResponseEntity<Void> listInteractions(
      @PathVariable("caseId") Long caseId,
      @RequestParam(name = "status", required = false) String status,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/interactions/{interactionId}/answer")
  public ResponseEntity<Void> answerInteraction(
      @PathVariable("caseId") Long caseId,
      @PathVariable("interactionId") Long interactionId,
      @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/runs")
  public ResponseEntity<Void> startRun(
      @PathVariable("caseId") Long caseId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/runs")
  public ResponseEntity<Void> listRuns(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/policy-evidences")
  public ResponseEntity<Void> listPolicyEvidences(@PathVariable("caseId") Long caseId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{caseId}/email-messages")
  public ResponseEntity<Void> listEmailMessages(
      @PathVariable("caseId") Long caseId,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/replies/analyze")
  public ResponseEntity<Void> analyzeReply(
      @PathVariable("caseId") Long caseId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/refund-confirmation")
  public ResponseEntity<Void> confirmRefund(
      @PathVariable("caseId") Long caseId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{caseId}/close")
  public ResponseEntity<Void> closeCase(
      @PathVariable("caseId") Long caseId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
