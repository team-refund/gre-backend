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
  @GetMapping("/{draftId}")
  public ResponseEntity<Void> getDraft(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PutMapping("/{draftId}")
  public ResponseEntity<Void> updateDraft(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{draftId}/messages")
  public ResponseEntity<Void> createMessage(
      @PathVariable("draftId") Long draftId, @RequestBody JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{draftId}/messages")
  public ResponseEntity<Void> listMessages(
      @PathVariable("draftId") Long draftId,
      @RequestParam(name = "page", required = false) Integer page,
      @RequestParam(name = "size", required = false) Integer size) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{draftId}/approve")
  public ResponseEntity<Void> approveDraft(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/{draftId}/send")
  public ResponseEntity<Void> sendDraft(
      @PathVariable("draftId") Long draftId, @RequestBody(required = false) JsonNode request) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/{draftId}/send-attempts")
  public ResponseEntity<Void> listSendAttempts(@PathVariable("draftId") Long draftId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
