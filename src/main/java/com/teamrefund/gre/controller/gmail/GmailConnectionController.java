package com.teamrefund.gre.controller.gmail;

import com.teamrefund.gre.dto.request.gmail.CreateGmailDraftRequest;
import com.teamrefund.gre.dto.response.gmail.GmailConnectionResponse;
import com.teamrefund.gre.dto.response.gmail.GmailDisconnectResponse;
import com.teamrefund.gre.dto.response.gmail.GmailMessageSearchResponse;
import com.teamrefund.gre.dto.response.gmail.GmailReconnectResponse;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.gmail.GmailAuthorizationService;
import com.teamrefund.gre.service.gmail.GmailConnectionService;
import com.teamrefund.gre.service.gmail.GmailDraftService;
import com.teamrefund.gre.service.gmail.GmailMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gmail")
public class GmailConnectionController {
  private final GmailConnectionService gmailConnectionService;
  private final GmailAuthorizationService gmailAuthorizationService;
  private final GmailMessageService gmailMessageService;
  private final GmailDraftService gmailDraftService;
  private final HttpSessionOAuth2AuthorizationRequestRepository authorizationRequests;

  private final URI successRedirectUri;

  public GmailConnectionController(
      GmailConnectionService gmailConnectionService,
      GmailAuthorizationService gmailAuthorizationService,
      GmailMessageService gmailMessageService,
      GmailDraftService gmailDraftService,
      HttpSessionOAuth2AuthorizationRequestRepository authorizationRequests,
      @Value("${gre.gmail.oauth.success-redirect-uri}") String successRedirectUri) {
    this.gmailConnectionService = gmailConnectionService;
    this.gmailAuthorizationService = gmailAuthorizationService;
    this.gmailMessageService = gmailMessageService;
    this.gmailDraftService = gmailDraftService;
    this.authorizationRequests = authorizationRequests;
    this.successRedirectUri = successRedirectUri.isBlank() ? null : URI.create(successRedirectUri);
  }

  // 연결된 Gmail 계정에 이메일 초안을 생성합니다.
  @PostMapping("/drafts")
  public ResponseEntity<Map<String, String>> createDraft(
      Principal principal, @Valid @RequestBody CreateGmailDraftRequest request) {
    var draftId =
        gmailDraftService.createDraft(
            principal.getName(), request.getTo(), request.getSubject(), request.getBody());
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("draftId", draftId));
  }

  // 기존 Gmail 연결의 재연동을 시작합니다.
  @PostMapping("/connection/reconnect")
  public ResponseEntity<GmailReconnectResponse> reconnect(
      Principal principal, HttpServletRequest request, HttpServletResponse response) {
    var authorization = gmailAuthorizationService.startReconnect(principal.getName());
    authorizationRequests.saveAuthorizationRequest(authorization, request, response);
    return ResponseEntity.ok()
        .cacheControl(CacheControl.noStore())
        .body(new GmailReconnectResponse(authorization.getAuthorizationRequestUri()));
  }

  // Gmail 계정 연결을 해제합니다.
  @DeleteMapping("/connection")
  public GmailDisconnectResponse disconnect(Principal principal) {
    var revokeConfirmed = gmailAuthorizationService.disconnect(principal.getName());
    return new GmailDisconnectResponse(GmailConnectionStatus.DISCONNECTED, revokeConfirmed);
  }

  // 현재 Gmail 연동 상태를 조회합니다.
  @GetMapping("/connection")
  public GmailConnectionResponse getConnection(Principal principal) {
    var connection = gmailConnectionService.getConnection(principal.getName());
    return new GmailConnectionResponse(
        connection.getStatus(), connection.getGoogleEmail(), connection.getConnectedAt());
  }

  // Google의 Gmail 권한 동의 화면으로 이동합니다.
  @GetMapping("/oauth2/authorize")
  public ResponseEntity<Void> authorize(
      Principal principal, HttpServletRequest request, HttpServletResponse response) {
    var authorization = gmailAuthorizationService.startAuthorization(principal.getName());
    authorizationRequests.saveAuthorizationRequest(authorization, request, response);
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create(authorization.getAuthorizationRequestUri()))
        .build();
  }

  // 연결된 Gmail 계정에서 메일을 검색합니다.
  @GetMapping("/messages")
  public GmailMessageSearchResponse searchMessages(
      Principal principal,
      @RequestParam(name = "q", required = false) String query,
      @RequestParam(name = "pageToken", required = false) String pageToken) {
    var result = gmailMessageService.search(principal.getName(), query, pageToken);
    var messages =
        result.getMessages().stream()
            .map(
                message ->
                    new GmailMessageSearchResponse.Message(
                        message.getMessageId(),
                        message.getThreadId(),
                        message.getFrom(),
                        message.getSubject(),
                        message.getSnippet(),
                        message.getReceivedAt()))
            .toList();
    return new GmailMessageSearchResponse(messages, result.getNextPageToken());
  }

  // Google 권한 동의 결과를 받아 Gmail 연결을 저장합니다.
  @GetMapping("/oauth2/callback")
  public ResponseEntity<String> callback(
      Principal principal,
      HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam(name = "code", required = false) String code,
      @RequestParam(name = "error", required = false) String error) {
    var authorization = authorizationRequests.removeAuthorizationRequest(request, response);
    if (authorization == null || error != null || code == null || code.isBlank()) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_INVALID);
    }
    gmailAuthorizationService.exchangeCode(principal.getName(), authorization, code);
    if (successRedirectUri != null) {
      return ResponseEntity.status(HttpStatus.SEE_OTHER).location(successRedirectUri).build();
    }
    return ResponseEntity.ok("Gmail connection saved.");
  }
}
