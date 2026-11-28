package com.teamrefund.gre.controller.orchestrator;

import com.teamrefund.gre.dto.response.orchestrator.OrchestratorRunResponse;
import com.teamrefund.gre.service.orchestrator.OrchestratorRunQueryService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orchestrator-runs")
@RequiredArgsConstructor
public class OrchestratorRunController {
  private final OrchestratorRunQueryService runQueryService;

  // AI 실행의 진행 상태와 결과를 조회합니다.
  @GetMapping("/{runId}")
  public OrchestratorRunResponse getRun(Principal principal, @PathVariable("runId") long runId) {
    return new OrchestratorRunResponse(runQueryService.getRun(principal.getName(), runId));
  }

  // AI 실행 상태 변경을 SSE로 구독합니다.
  @GetMapping(path = "/{runId}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Void> streamEvents(@PathVariable("runId") Long runId) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }
}
