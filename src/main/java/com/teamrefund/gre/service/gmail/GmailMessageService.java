package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.dto.query.gmail.GmailMessageSearchResult;
import com.teamrefund.gre.dto.query.gmail.GmailMessageSummary;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriBuilder;
import tools.jackson.databind.JsonNode;

@Service
public class GmailMessageService {
  private static final String MESSAGES_PATH = "/gmail/v1/users/me/messages";
  private static final int PAGE_SIZE = 10;

  private final GmailConnectionService connectionService;
  private final GmailConnectionDao connectionDao;
  private final GmailTokenCipher tokenCipher;
  private final RestClient gmailClient;

  public GmailMessageService(
      GmailConnectionService connectionService,
      GmailConnectionDao connectionDao,
      GmailTokenCipher tokenCipher,
      @Qualifier("gmailApiClient") RestClient gmailClient) {
    this.connectionService = connectionService;
    this.connectionDao = connectionDao;
    this.tokenCipher = tokenCipher;
    this.gmailClient = gmailClient;
  }

  public GmailMessageSearchResult search(String loginId, String query, String pageToken) {
    var token = getAccessToken(loginId);
    try {
      var page = fetchMessagePage(token, query, pageToken);
      var messages = new ArrayList<GmailMessageSummary>();
      for (var item : page.path("messages")) {
        messages.add(fetchMessageSummary(token, item.path("id").asString()));
      }
      return new GmailMessageSearchResult(
          List.copyOf(messages), page.path("nextPageToken").asString(null));
    } catch (RestClientResponseException exception) {
      if (exception.getStatusCode().value() == 401) {
        throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
      }
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    } catch (RestClientException exception) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
  }

  private JsonNode fetchMessagePage(String accessToken, String query, String pageToken) {
    var page =
        gmailClient
            .get()
            .uri(builder -> buildMessageListUri(builder, query, pageToken))
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .body(JsonNode.class);
    if (page == null || !page.isObject()) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
    return page;
  }

  private URI buildMessageListUri(UriBuilder builder, String query, String pageToken) {
    builder.path(MESSAGES_PATH).queryParam("maxResults", PAGE_SIZE);
    var parameters = new HashMap<String, String>();
    if (query != null && !query.isBlank()) {
      builder.queryParam("q", "{query}");
      parameters.put("query", query);
    }
    if (pageToken != null && !pageToken.isBlank()) {
      builder.queryParam("pageToken", "{pageToken}");
      parameters.put("pageToken", pageToken);
    }
    return builder.build(parameters);
  }

  private String getAccessToken(String loginId) {
    var connection = connectionService.getConnection(loginId);
    if (connection.getStatus() == GmailConnectionStatus.REAUTH_REQUIRED) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    if (connection.getStatus() != GmailConnectionStatus.CONNECTED) {
      throw new BusinessException(ErrorCode.GMAIL_NOT_CONNECTED);
    }
    var principalName = "gre-user:" + connection.getUserId();
    if (!"gmail".equals(connection.getOauthClientRegistrationId())
        || !principalName.equals(connection.getOauthPrincipalName())) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    var client = connectionDao.findAuthorizedClient(principalName);
    if (client == null || !client.getAccessTokenExpiresAt().isAfter(Instant.now())) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    return tokenCipher.decrypt(client.getAccessTokenValue(), principalName + ":access");
  }

  private GmailMessageSummary fetchMessageSummary(String accessToken, String messageId) {
    var message =
        gmailClient
            .get()
            .uri(
                builder ->
                    builder
                        .path(MESSAGES_PATH + "/{id}")
                        .queryParam("format", "metadata")
                        .queryParam("metadataHeaders", "From", "Subject")
                        .queryParam("fields", "id,threadId,snippet,internalDate,payload(headers)")
                        .build(messageId))
            .headers(headers -> headers.setBearerAuth(accessToken))
            .retrieve()
            .body(JsonNode.class);
    if (message == null || !message.isObject()) {
      throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
    }
    var headers = message.path("payload").path("headers");
    return new GmailMessageSummary(
        message.path("id").asString(),
        message.path("threadId").asString(),
        findHeader(headers, "From"),
        findHeader(headers, "Subject"),
        message.path("snippet").asString(null),
        Instant.ofEpochMilli(message.path("internalDate").asLong()));
  }

  private String findHeader(JsonNode headers, String name) {
    for (var header : headers) {
      if (name.equalsIgnoreCase(header.path("name").asString())) {
        return header.path("value").asString(null);
      }
    }
    return null;
  }
}
