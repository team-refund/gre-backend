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
      client.setRefreshTokenValue(
          tokenCipher.encrypt(
              refreshToken.getTokenValue(), client.getPrincipalName() + ":refresh"));
      client.setRefreshTokenIssuedAt(refreshToken.getIssuedAt());
    }

    gmailConnectionDao.upsertAuthorizedClient(client);
    gmailConnectionDao.upsertConnection(userId, googleEmail, client.getPrincipalName());
  }

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

  private boolean matchesConnectedAccount(
      GmailConnection connection, String googleEmail, String principalName) {
    return connection != null
        && connection.getStatus() == GmailConnectionStatus.CONNECTED
        && Objects.equals(connection.getGoogleEmail(), googleEmail)
        && GMAIL_REGISTRATION_ID.equals(connection.getOauthClientRegistrationId())
        && principalName.equals(connection.getOauthPrincipalName());
  }
}
