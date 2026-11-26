package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.time.Instant;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GmailAccessTokenService {
  static final String COMPOSE_SCOPE = "https://www.googleapis.com/auth/gmail.compose";

  private final GmailConnectionService connectionService;
  private final GmailConnectionDao connectionDao;
  private final GmailTokenCipher tokenCipher;
  private final GmailTokenRefreshService tokenRefreshService;

  String getReadToken(String loginId) {
    return getAccessToken(loginId, null);
  }

  String getComposeToken(String loginId) {
    return getAccessToken(loginId, COMPOSE_SCOPE);
  }

  private String getAccessToken(String loginId, String requiredScope) {
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
    if (client == null) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    verifyScope(client, requiredScope);
    if (!client.getAccessTokenExpiresAt().isAfter(Instant.now())) {
      tokenRefreshService.refresh(connection.getUserId(), loginId, client);
      client = connectionDao.findAuthorizedClient(principalName);
      if (client == null || !client.getAccessTokenExpiresAt().isAfter(Instant.now())) {
        throw new BusinessException(ErrorCode.GMAIL_TOKEN_REFRESH_FAILED);
      }
      verifyScope(client, requiredScope);
    }
    return tokenCipher.decrypt(client.getAccessTokenValue(), principalName + ":access");
  }

  private void verifyScope(GmailAuthorizedClient client, String requiredScope) {
    if (requiredScope != null
        && (client.getAccessTokenScopes() == null
            || !Arrays.asList(client.getAccessTokenScopes().split(",")).contains(requiredScope))) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
  }
}
