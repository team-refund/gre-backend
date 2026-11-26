package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dao.gmail.GmailConnectionDao;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GmailTokenStorageService {
  private static final String GMAIL_REGISTRATION_ID = "gmail";
  private static final String PRINCIPAL_PREFIX = "gre-user:";

  private final GmailConnectionDao gmailConnectionDao;
  private final GmailTokenCipher tokenCipher;

  /** 현재 연결의 토큰인지 확인한 뒤 갱신 결과를 저장하고 생략된 값은 기존 값으로 유지합니다. */
  @Transactional
  public void saveRefreshedToken(
      long userId,
      String loginId,
      GmailAuthorizedClient previous,
      OAuth2AccessTokenResponse tokens) {
    verifyCurrentToken(userId, loginId, previous);
    var accessToken = tokens.getAccessToken();
    var client = createAuthorizedClient(userId, accessToken);
    if (accessToken.getScopes().isEmpty()) {
      client.setAccessTokenScopes(previous.getAccessTokenScopes());
    }
    var refreshToken = tokens.getRefreshToken();
    if (refreshToken == null) {
      client.setRefreshTokenValue(previous.getRefreshTokenValue());
      client.setRefreshTokenIssuedAt(previous.getRefreshTokenIssuedAt());
    } else {
      setRefreshToken(client, refreshToken);
    }
    gmailConnectionDao.upsertAuthorizedClient(client);
  }

  /** 토큰 교체 여부를 확인한 뒤 해당 연결을 재인증 필요 상태로 바꿉니다. */
  @Transactional
  public void markReauthRequired(long userId, String loginId, GmailAuthorizedClient previous) {
    verifyCurrentToken(userId, loginId, previous);
    gmailConnectionDao.updateReauthRequired(userId, previous.getPrincipalName());
  }

  /** 사용자 행을 잠그고 갱신 요청을 보낼 때의 연결과 토큰이 여전히 유효한지 확인합니다. */
  private void verifyCurrentToken(long userId, String loginId, GmailAuthorizedClient previous) {
    if (gmailConnectionDao.lockActiveUser(userId, loginId) == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    var connection = gmailConnectionDao.findForActiveUser(loginId);
    if (connection == null
        || connection.getStatus() != GmailConnectionStatus.CONNECTED
        || !GMAIL_REGISTRATION_ID.equals(connection.getOauthClientRegistrationId())
        || !previous.getPrincipalName().equals(connection.getOauthPrincipalName())) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    // A reconnect or another refresh might have replaced these tokens during the Google request.
    var current = gmailConnectionDao.findAuthorizedClient(previous.getPrincipalName());
    if (current == null
        || !Arrays.equals(current.getAccessTokenValue(), previous.getAccessTokenValue())
        || !Arrays.equals(current.getRefreshTokenValue(), previous.getRefreshTokenValue())) {
      throw new BusinessException(ErrorCode.GMAIL_TOKEN_REFRESH_FAILED);
    }
  }

  /** 사용자 소유권을 확인하고 암호화한 토큰과 Gmail 연결 정보를 함께 저장합니다. */
  @Transactional
  public void save(
      long userId, String loginId, String googleEmail, OAuth2AccessTokenResponse tokens) {
    if (gmailConnectionDao.lockActiveUser(userId, loginId) == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    var accessToken = tokens.getAccessToken();
    var client = createAuthorizedClient(userId, accessToken);
    var refreshToken = tokens.getRefreshToken();
    if (refreshToken == null) {
      reuseRefreshToken(loginId, googleEmail, client, accessToken.getScopes());
    } else {
      setRefreshToken(client, refreshToken);
    }

    gmailConnectionDao.upsertAuthorizedClient(client);
    gmailConnectionDao.upsertConnection(userId, googleEmail, client.getPrincipalName());
  }

  /** Gmail 연결의 OAuth 참조를 먼저 해제한 뒤 저장된 토큰을 삭제합니다. */
  @Transactional
  public GmailAuthorizedClient disconnect(String loginId, long userId) {
    if (gmailConnectionDao.lockActiveUser(userId, loginId) == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    var connection = gmailConnectionDao.findForActiveUser(loginId);
    if (connection == null) {
      throw new BusinessException(ErrorCode.FORBIDDEN);
    }
    if (connection.getStatus() == GmailConnectionStatus.DISCONNECTED) {
      return null;
    }

    var principalName = connection.getOauthPrincipalName();
    var client =
        GMAIL_REGISTRATION_ID.equals(connection.getOauthClientRegistrationId())
                && (PRINCIPAL_PREFIX + userId).equals(principalName)
            ? gmailConnectionDao.findAuthorizedClient(principalName)
            : null;
    gmailConnectionDao.disconnect(userId);
    if (client != null) {
      gmailConnectionDao.deleteAuthorizedClient(principalName);
    }
    return client;
  }

  /** 액세스 토큰을 암호화하고 DB에 저장할 OAuth 클라이언트 정보를 구성합니다. */
  private GmailAuthorizedClient createAuthorizedClient(long userId, OAuth2AccessToken accessToken) {
    var client = new GmailAuthorizedClient();
    client.setClientRegistrationId(GMAIL_REGISTRATION_ID);
    client.setPrincipalName(PRINCIPAL_PREFIX + userId);
    client.setAccessTokenType(accessToken.getTokenType().getValue());
    client.setAccessTokenValue(
        tokenCipher.encrypt(accessToken.getTokenValue(), client.getPrincipalName() + ":access"));
    client.setAccessTokenIssuedAt(accessToken.getIssuedAt());
    client.setAccessTokenExpiresAt(accessToken.getExpiresAt());
    client.setAccessTokenScopes(String.join(",", new TreeSet<>(accessToken.getScopes())));
    return client;
  }

  /** 리프레시 토큰을 암호화하고 발급 시각과 함께 저장 객체에 채웁니다. */
  private void setRefreshToken(GmailAuthorizedClient client, OAuth2RefreshToken refreshToken) {
    client.setRefreshTokenValue(
        tokenCipher.encrypt(refreshToken.getTokenValue(), client.getPrincipalName() + ":refresh"));
    client.setRefreshTokenIssuedAt(refreshToken.getIssuedAt());
  }

  /** 동일한 Google 계정과 필요한 권한이 확인될 때 기존 리프레시 토큰을 재사용합니다. */
  private void reuseRefreshToken(
      String loginId,
      String googleEmail,
      GmailAuthorizedClient client,
      Set<String> requiredScopes) {
    var connection = gmailConnectionDao.findForActiveUser(loginId);
    if (!matchesConnectedAccount(connection, googleEmail, client.getPrincipalName())) {
      throw new BusinessException(ErrorCode.GMAIL_REFRESH_TOKEN_REQUIRED);
    }

    var previous = gmailConnectionDao.findAuthorizedClient(client.getPrincipalName());
    if (previous == null
        || previous.getRefreshTokenValue() == null
        || previous.getAccessTokenScopes() == null
        || !Arrays.asList(previous.getAccessTokenScopes().split(",")).containsAll(requiredScopes)) {
      throw new BusinessException(ErrorCode.GMAIL_REFRESH_TOKEN_REQUIRED);
    }
    client.setRefreshTokenValue(previous.getRefreshTokenValue());
    client.setRefreshTokenIssuedAt(previous.getRefreshTokenIssuedAt());
  }

  /** 연결 상태, Google 이메일, OAuth 참조가 같은 계정을 가리키는지 확인합니다. */
  private boolean matchesConnectedAccount(
      GmailConnection connection, String googleEmail, String principalName) {
    return connection != null
        && connection.getStatus() == GmailConnectionStatus.CONNECTED
        && Objects.equals(connection.getGoogleEmail(), googleEmail)
        && GMAIL_REGISTRATION_ID.equals(connection.getOauthClientRegistrationId())
        && principalName.equals(connection.getOauthPrincipalName());
  }
}
