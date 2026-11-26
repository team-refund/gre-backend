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
  @GetMapping("/{runId}")
  public ResponseEntity<Void> getRun(@PathVariable("runId") Long runId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping(path = "/{runId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Void> streamEvents(@PathVariable("runId") Long runId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
