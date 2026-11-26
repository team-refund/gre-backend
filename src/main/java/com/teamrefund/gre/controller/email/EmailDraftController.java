package com.teamrefund.gre.controller.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/email-drafts")
public class EmailDraftController {
  // 최신 이메일 초안의 상세 정보와 미리보기를 조회합니다.
  @GetMapping("/{draftId}")
  public ResponseEntity<Void> getDraft(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 현재 이메일 초안의 내용을 직접 수정합니다.
  @PutMapping("/{draftId}")
  public ResponseEntity<Void> updateDraft(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // AI에 초안 작성 또는 수정을 요청하고 결과를 NDJSON으로 받습니다.
  @PostMapping("/{draftId}/messages")
  public ResponseEntity<Void> createMessage(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 이메일 초안 작성 대화 이력을 조회합니다.
  @GetMapping("/{draftId}/messages")
  public ResponseEntity<Void> listMessages(
      @PathVariable("draftId") Long draftId,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 현재 이메일 초안을 승인합니다.
  @PostMapping("/{draftId}/approve")
  public ResponseEntity<Void> approveDraft(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 승인된 이메일 초안의 Gmail 발송을 요청합니다.
  @PostMapping("/{draftId}/send")
  public ResponseEntity<Void> sendDraft(
      @PathVariable("draftId") Long draftId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // 이메일 발송 시도 이력과 상태를 조회합니다.
  @GetMapping("/{draftId}/send-attempts")
  public ResponseEntity<Void> listSendAttempts(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
