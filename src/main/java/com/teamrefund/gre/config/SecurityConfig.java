package com.teamrefund.gre.config;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration(proxyBeanMethods = false)
class SecurityConfig {

  @Bean
  SecurityContextRepository securityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      SecurityContextRepository securityContextRepository,
      @Value("${gre.dev.test-login-enabled}") boolean testLoginEnabled,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver)
      throws Exception {
    AuthenticationEntryPoint entryPoint =
        (request, response, exception) -> {
          response.setHeader("WWW-Authenticate", "Basic realm=\"GRE\"");
          resolver.resolveException(
              request, response, null, new BusinessException(ErrorCode.UNAUTHORIZED));
        };
    var testLogin =
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/dev/test-login");
    if (testLoginEnabled) {
      http.csrf(csrf -> csrf.ignoringRequestMatchers(testLogin));
    }
    return http.authorizeHttpRequests(
            requests -> {
              if (testLoginEnabled) {
                requests.requestMatchers(testLogin).permitAll();
              }
              requests
                  .dispatcherTypeMatchers(DispatcherType.ERROR)
                  .permitAll()
                  .requestMatchers("/api/gmail/**")
                  .hasRole("GMAIL_TEST")
                  .anyRequest()
                  .denyAll();
            })
        .securityContext(context -> context.securityContextRepository(securityContextRepository))
        .exceptionHandling(
            errors ->
                errors
                    .authenticationEntryPoint(entryPoint)
                    .accessDeniedHandler(
                        (request, response, exception) ->
                            resolver.resolveException(
                                request,
                                response,
                                null,
                                new BusinessException(ErrorCode.FORBIDDEN))))
        .httpBasic(basic -> basic.authenticationEntryPoint(entryPoint))
        .build();
  }

  @Bean
  UserDetailsService localGmailTestUser(@Value("${gre.gmail.test-password}") String password) {
    if (password.isBlank()) {
      return new InMemoryUserDetailsManager();
    }
    var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    return new InMemoryUserDetailsManager(
        User.withUsername("gmail-test")
            .password(encoder.encode(password))
            .roles("GMAIL_TEST")
            .build());
  }
}
