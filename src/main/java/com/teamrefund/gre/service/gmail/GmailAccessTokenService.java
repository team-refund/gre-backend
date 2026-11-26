package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
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

  /** 메일 읽기에 사용할 Gmail 액세스 토큰을 반환합니다. */
  String getReadToken(String loginId) {
    return getAccessToken(loginId, null);
  }

  /** 메일 작성 권한을 확인하고 Gmail 토큰을 반환합니다. */
  String getComposeToken(String loginId) {
    return getAccessToken(loginId, COMPOSE_SCOPE);
  }

  /** 현재 연결을 조회해 해당 계정의 토큰을 가져옵니다. */
  private String getAccessToken(String loginId, String requiredScope) {
    var connection = connectionService.getConnection(loginId);
    return getAccessToken(loginId, requiredScope, connection);
  }

  /** 발송을 선점한 Gmail 계정이 토큰 조회 중에도 유지됐는지 확인합니다. */
  String getComposeTokenForConnection(String loginId, GmailConnection expectedConnection) {
    var current = connectionService.getConnection(loginId);
    if (!matchesConnection(current, expectedConnection)) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    var token = getAccessToken(loginId, COMPOSE_SCOPE, current);
    var latest = connectionService.getConnection(loginId);
    if (latest.getStatus() != GmailConnectionStatus.CONNECTED
        || !matchesConnection(latest, expectedConnection)) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    return token;
  }

  /** 연결 ID와 Google 이메일이 같은지 확인합니다. */
  private boolean matchesConnection(GmailConnection actual, GmailConnection expected) {
    return Objects.equals(actual.getId(), expected.getId())
        && Objects.equals(actual.getGoogleEmail(), expected.getGoogleEmail());
  }

  /** 연결과 권한을 확인하고 필요하면 토큰을 갱신한 뒤 복호화합니다. */
  private String getAccessToken(String loginId, String requiredScope, GmailConnection connection) {
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

  /** 저장된 토큰에 필요한 Gmail 권한이 있는지 확인합니다. */
  private void verifyScope(GmailAuthorizedClient client, String requiredScope) {
    if (requiredScope != null
        && (client.getAccessTokenScopes() == null
            || !Arrays.asList(client.getAccessTokenScopes().split(",")).contains(requiredScope))) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
  }
}
