package com.teamrefund.gre.controller.gmail;

import com.teamrefund.gre.dto.response.gmail.GmailConnectionResponse;
import com.teamrefund.gre.dto.response.gmail.GmailMessageSearchResponse;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.gmail.GmailAuthorizationService;
import com.teamrefund.gre.service.gmail.GmailConnectionService;
import com.teamrefund.gre.service.gmail.GmailMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gmail")
@RequiredArgsConstructor
public class GmailConnectionController {
  private final GmailConnectionService gmailConnectionService;
  private final GmailAuthorizationService gmailAuthorizationService;
  private final GmailMessageService gmailMessageService;
  private final HttpSessionOAuth2AuthorizationRequestRepository authorizationRequests;

  @PostMapping("/drafts")
  public ResponseEntity<Void> createDraft() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @PostMapping("/connection/reconnect")
  public ResponseEntity<Void> reconnect() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @DeleteMapping("/connection")
  public ResponseEntity<Void> disconnect() {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
  }

  @GetMapping("/connection")
  public GmailConnectionResponse getConnection(Principal principal) {
    var connection = gmailConnectionService.getConnection(principal.getName());
    return new GmailConnectionResponse(
        connection.getStatus(), connection.getGoogleEmail(), connection.getConnectedAt());
  }

  @GetMapping("/oauth2/authorize")
  public ResponseEntity<Void> authorize(
      Principal principal, HttpServletRequest request, HttpServletResponse response) {
    var authorization = gmailAuthorizationService.startAuthorization(principal.getName());
    authorizationRequests.saveAuthorizationRequest(authorization, request, response);
    return ResponseEntity.status(HttpStatus.FOUND)
        .location(URI.create(authorization.getAuthorizationRequestUri()))
        .build();
  }

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
    return ResponseEntity.ok("Gmail connection saved.");
  }
}
