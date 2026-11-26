package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dto.query.gmail.CaseEmailSaveResult;
import com.teamrefund.gre.dto.query.gmail.GmailThreadMessage;
import com.teamrefund.gre.dto.query.gmail.GmailThreadSearchResult;
import com.teamrefund.gre.dto.query.gmail.GmailThreadSearchResult.ThreadSummary;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;

@Service
public class GmailThreadService {
  private static final String THREADS_PATH = "/gmail/v1/users/me/threads";
  private static final int PAGE_SIZE = 20;
  private static final int MAX_PAGE_TOKEN_LENGTH = 2048;
  private static final int MAX_SUBJECT_LENGTH = 500;
  private static final String MERCHANT_QUERY = "{from:%1$s to:%1$s cc:%1$s bcc:%1$s} -in:drafts";
  private static final Pattern SEARCHABLE_EMAIL = Pattern.compile("[a-z0-9._+%'-]+@[a-z0-9.-]+");
  private static final Pattern THREAD_ID = Pattern.compile("[a-zA-Z0-9_-]{1,255}");

  private final GmailConnectionService connectionService;
  private final GmailAccessTokenService accessTokenService;
  private final CaseEmailDao caseEmailDao;
  private final CaseEmailStorageService storageService;
  private final RestClient gmailClient;

  public GmailThreadService(
      GmailConnectionService connectionService,
      GmailAccessTokenService accessTokenService,
      CaseEmailDao caseEmailDao,
      CaseEmailStorageService storageService,
      @Qualifier("gmailApiClient") RestClient gmailClient) {
    this.connectionService = connectionService;
    this.accessTokenService = accessTokenService;
    this.caseEmailDao = caseEmailDao;
    this.storageService = storageService;
    this.gmailClient = gmailClient;
  }

  public GmailThreadSearchResult search(String loginId, String merchantEmail, String pageToken) {
    var email = validateMerchantEmail(merchantEmail);
    if (pageToken != null && pageToken.length() > MAX_PAGE_TOKEN_LENGTH) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var accessToken = accessTokenService.getReadToken(loginId);
    var page = fetchThreadPage(accessToken, MERCHANT_QUERY.formatted(email), pageToken);

    var threads = new ArrayList<ThreadSummary>();
    for (var thread : page.path("threads")) {
      threads.add(
          new ThreadSummary(requiredString(thread, "id"), thread.path("snippet").asString(null)));
    }
    return new GmailThreadSearchResult(
        List.copyOf(threads), page.path("nextPageToken").asString(null));
  }

  public CaseEmailSaveResult save(
      String loginId, long caseId, String merchantEmail, String gmailThreadId) {
    var email = validateMerchantEmail(merchantEmail);
    if (caseId <= 0 || gmailThreadId == null || !THREAD_ID.matcher(gmailThreadId).matches()) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var connection = connectionService.getConnection(loginId);
    if (!caseEmailDao.existsOwnedCase(caseId, connection.getUserId())) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    var accessToken = accessTokenService.getReadToken(loginId);
    var thread = fetchThread(accessToken, gmailThreadId);
    if (!gmailThreadId.equals(requiredString(thread, "id"))) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
    var messages = readMessages(thread, email, connection.getGoogleEmail());
    return storageService.save(caseId, connection, gmailThreadId, messages);
  }

  private JsonNode fetchThreadPage(String accessToken, String query, String pageToken) {
    return callGmail(
        () ->
            gmailClient
                .get()
                .uri(
                    builder -> {
                      builder
                          .path(THREADS_PATH)
                          .queryParam("maxResults", PAGE_SIZE)
                          .queryParam("q", "{query}");
                      var parameters = new HashMap<String, String>();
                      parameters.put("query", query);
                      if (pageToken != null && !pageToken.isBlank()) {
                        builder.queryParam("pageToken", "{pageToken}");
                        parameters.put("pageToken", pageToken);
                      }
                      return builder.build(parameters);
                    })
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(JsonNode.class));
  }

  private JsonNode fetchThread(String accessToken, String gmailThreadId) {
    return callGmail(
        () ->
            gmailClient
                .get()
                .uri(
                    builder ->
                        builder
                            .path(THREADS_PATH + "/{id}")
                            .queryParam("format", "full")
                            .build(gmailThreadId))
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .body(JsonNode.class));
  }

  private List<GmailThreadMessage> readMessages(
      JsonNode thread, String merchantEmail, String ownEmail) {
    var messages = new ArrayList<GmailThreadMessage>();
    boolean hasMerchant = false;
    for (var message : thread.path("messages")) {
      if (hasLabel(message, "DRAFT")) {
        continue;
      }
      var payload = message.path("payload");
      var sender = addresses(payload, "From").stream().findFirst().orElse(null);
      if (sender == null || sender.length() > 320) {
        throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
      }
      var recipients = getRecipients(payload);
      hasMerchant =
          hasMerchant
              || merchantEmail.equalsIgnoreCase(sender)
              || recipients.stream().anyMatch(merchantEmail::equalsIgnoreCase);
      var direction =
          hasLabel(message, "SENT") || sender.equalsIgnoreCase(ownEmail) ? "OUTBOUND" : "INBOUND";
      var date = message.path("internalDate").asLong(-1);
      messages.add(
          new GmailThreadMessage(
              requiredString(message, "id"),
              sender,
              recipients.isEmpty() ? null : String.join(", ", recipients),
              getSubject(payload),
              GmailThreadBody.readText(payload),
              direction,
              date >= 0 ? Instant.ofEpochMilli(date) : null));
    }
    if (!hasMerchant) {
      throw new BusinessException(ErrorCode.GMAIL_THREAD_MERCHANT_MISMATCH);
    }
    return List.copyOf(messages);
  }

  private List<String> getRecipients(JsonNode payload) {
    var recipients = new ArrayList<>(addresses(payload, "To"));
    recipients.addAll(addresses(payload, "Cc"));
    recipients.addAll(addresses(payload, "Bcc"));
    return recipients;
  }

  private String getSubject(JsonNode payload) {
    var subject = header(payload, "Subject");
    if (subject == null || subject.codePointCount(0, subject.length()) <= MAX_SUBJECT_LENGTH) {
      return subject;
    }
    return subject.substring(0, subject.offsetByCodePoints(0, MAX_SUBJECT_LENGTH));
  }

  private List<String> addresses(JsonNode payload, String name) {
    var value = header(payload, name);
    if (value == null || value.isBlank()) {
      return List.of();
    }
    try {
      return Arrays.stream(InternetAddress.parseHeader(value, false))
          .map(InternetAddress::getAddress)
          .filter(address -> address != null && !address.isBlank())
          .toList();
    } catch (AddressException exception) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
  }

  static String header(JsonNode payload, String name) {
    for (var header : payload.path("headers")) {
      if (name.equalsIgnoreCase(header.path("name").asString(null))) {
        return header.path("value").asString(null);
      }
    }
    return null;
  }

  private boolean hasLabel(JsonNode message, String label) {
    for (var value : message.path("labelIds")) {
      if (label.equals(value.asString(null))) {
        return true;
      }
    }
    return false;
  }

  private String validateMerchantEmail(String email) {
    if (email == null || email.length() > 254) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    var normalized = email.strip().toLowerCase(Locale.ROOT);
    if (!SEARCHABLE_EMAIL.matcher(normalized).matches()) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    try {
      new InternetAddress(normalized, true).validate();
    } catch (AddressException exception) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    return normalized;
  }

  private String requiredString(JsonNode node, String field) {
    var value = node.path(field).asString(null);
    if (value == null || value.isBlank()) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
    return value;
  }

  private JsonNode callGmail(Supplier<JsonNode> request) {
    try {
      var result = request.get();
      if (result == null || !result.isObject()) {
        throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
      }
      return result;
    } catch (RestClientResponseException exception) {
      if (exception.getStatusCode().value() == 401) {
        throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
      }
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    } catch (RestClientException exception) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
  }
}
