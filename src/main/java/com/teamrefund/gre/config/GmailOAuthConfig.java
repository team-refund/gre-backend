package com.teamrefund.gre.config;

import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.endpoint.RestClientAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
class GmailOAuthConfig {
  @Bean
  RestClient gmailApiClient() {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout(Duration.ofSeconds(5));
    factory.setReadTimeout(Duration.ofSeconds(10));
    return RestClient.builder()
        .baseUrl("https://gmail.googleapis.com")
        .requestFactory(factory)
        .build();
  }

  @Bean
  RestClientAuthorizationCodeTokenResponseClient gmailTokenClient() {
    return new RestClientAuthorizationCodeTokenResponseClient();
  }

  @Bean
  HttpSessionOAuth2AuthorizationRequestRepository gmailAuthorizationRequests() {
    return new HttpSessionOAuth2AuthorizationRequestRepository();
  }
}
