package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.teamrefund.gre.controller.gmail.GmailConnectionController;
import com.teamrefund.gre.entity.gmail.GmailAuthorizedClient;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.exception.GlobalExceptionHandler;
import java.util.Base64;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

class GmailConnectionControllerTest {
  private static final String LOGIN_ID = "test-user";
  private static final long USER_ID = 42L;
  private static final String REVOCATION_URI = "https://oauth2.googleapis.com/revoke";

  private final GmailConnectionService connections = mock(GmailConnectionService.class);
  private final GmailTokenStorageService tokens = mock(GmailTokenStorageService.class);
  private final RestClientAuthorizationCodeTokenResponseClient tokenClient =
      mock(RestClientAuthorizationCodeTokenResponseClient.class);
  private final GmailConnection connection = new GmailConnection();
  private final GmailTokenCipher cipher =
      new GmailTokenCipher(Base64.getEncoder().encodeToString(new byte[32]));
  private MockRestServiceServer google;
  private MockMvc mvc;

  @BeforeEach
  void setUp() {
    var googleClient = RestClient.builder().baseUrl("https://gmail.googleapis.com");
    google = MockRestServiceServer.bindTo(googleClient).build();
    var authorization =
        new GmailAuthorizationService(
            connections,
            tokenClient,
            googleClient.build(),
            tokens,
            cipher,
            "test-client-id",
            "test-client-secret",
            "http://localhost:8080/api/gmail/oauth2/callback");
    mvc =
        standaloneSetup(
                new GmailConnectionController(
                    connections,
                    authorization,
                    mock(GmailMessageService.class),
                    mock(GmailDraftService.class),
                    new HttpSessionOAuth2AuthorizationRequestRepository(),
                    "http://localhost:3000/me/gmail-connections"))
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    connection.setUserId(USER_ID);
    when(connections.getConnection(LOGIN_ID)).thenReturn(connection);
  }

  @Test
  void returnsGoogleConsentUrlAndSavesAuthorizationInTheSession() throws Exception {
    connection.setId(7L);

    var result =
        mvc.perform(post("/api/gmail/connection/reconnect").principal(() -> LOGIN_ID))
            .andExpect(status().isOk())
            .andExpect(header().string("Cache-Control", "no-store"))
            .andExpect(jsonPath("$.authorizationUrl").isString())
            .andReturn();

    assertThat(result.getResponse().getContentAsString())
        .contains("https://accounts.google.com/o/oauth2/v2/auth", "state=", "code_challenge=");
    assertThat(result.getRequest().getSession(false)).isNotNull();
  }

  @Test
  void reconnectRejectsUsersWithoutAPreviousConnection() throws Exception {
    mvc.perform(post("/api/gmail/connection/reconnect").principal(() -> LOGIN_ID))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("GMAIL_NOT_CONNECTED"));
  }

  @Test
  void disconnectReturnsUnconfirmedWhenNoTokenRemains() throws Exception {
    mvc.perform(delete("/api/gmail/connection").principal(() -> LOGIN_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("DISCONNECTED"))
        .andExpect(jsonPath("$.revokeConfirmed").value(false));

    verify(tokens).disconnect(LOGIN_ID, USER_ID);
    google.verify();
  }

  @Test
  void disconnectRevokesTheRefreshTokenAndReturnsConfirmation() throws Exception {
    stubStoredClient();
    google
        .expect(requestTo(REVOCATION_URI))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().string("token=test-refresh-token"))
        .andRespond(withSuccess());

    mvc.perform(delete("/api/gmail/connection").principal(() -> LOGIN_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("DISCONNECTED"))
        .andExpect(jsonPath("$.revokeConfirmed").value(true));

    google.verify();
  }

  @Test
  void disconnectReturnsUnconfirmedWhenGoogleRejectsRevocation() throws Exception {
    stubStoredClient();
    google.expect(requestTo(REVOCATION_URI)).andRespond(withBadRequest());

    mvc.perform(delete("/api/gmail/connection").principal(() -> LOGIN_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("DISCONNECTED"))
        .andExpect(jsonPath("$.revokeConfirmed").value(false));

    google.verify();
  }

  @Test
  void callbackUsesTheOriginalSessionAndRedirectsToTheFrontend() throws Exception {
    var authorization =
        mvc.perform(get("/api/gmail/oauth2/authorize").principal(() -> LOGIN_ID))
            .andExpect(status().isFound())
            .andReturn();
    var state =
        UriComponentsBuilder.fromUriString(authorization.getResponse().getHeader("Location"))
            .build()
            .getQueryParams()
            .getFirst("state");
    var session = (MockHttpSession) authorization.getRequest().getSession(false);
    var tokenResponse =
        OAuth2AccessTokenResponse.withToken("test-access-token")
            .tokenType(OAuth2AccessToken.TokenType.BEARER)
            .expiresIn(3600)
            .scopes(
                Set.of(
                    "https://www.googleapis.com/auth/gmail.readonly",
                    GmailAccessTokenService.COMPOSE_SCOPE))
            .build();
    when(tokenClient.getTokenResponse(any())).thenReturn(tokenResponse);
    google
        .expect(requestTo("https://gmail.googleapis.com/gmail/v1/users/me/profile"))
        .andRespond(
            withSuccess(
                "{\"emailAddress\":\"test-user@example.test\"}", MediaType.APPLICATION_JSON));

    mvc.perform(
            get("/api/gmail/oauth2/callback")
                .session(session)
                .principal(() -> LOGIN_ID)
                .param("code", "test-code")
                .param("state", state))
        .andExpect(status().isSeeOther())
        .andExpect(header().string("Location", "http://localhost:3000/me/gmail-connections"));

    verify(tokens).save(USER_ID, LOGIN_ID, "test-user@example.test", tokenResponse);
    google.verify();
  }

  private void stubStoredClient() {
    var client = new GmailAuthorizedClient();
    client.setPrincipalName("gre-user:42");
    client.setRefreshTokenValue(cipher.encrypt("test-refresh-token", "gre-user:42:refresh"));
    when(tokens.disconnect(LOGIN_ID, USER_ID)).thenReturn(client);
  }
}
