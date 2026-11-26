package com.teamrefund.gre.controller.orchestrator;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orchestrator-runs")
public class OrchestratorRunController {
  // AI 실행의 진행 상태와 결과를 조회합니다.
  @GetMapping("/{runId}")
  public ResponseEntity<Void> getRun(@PathVariable("runId") Long runId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  // AI 실행 상태 변경을 SSE로 구독합니다.
  @GetMapping(path = "/{runId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Void> streamEvents(@PathVariable("runId") Long runId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
