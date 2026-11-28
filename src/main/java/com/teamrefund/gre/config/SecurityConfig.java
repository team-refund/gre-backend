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
    var paths = PathPatternRequestMatcher.withDefaults();
    var testLogin = paths.matcher(HttpMethod.POST, "/api/dev/test-login");
    if (testLoginEnabled) {
      var createCase = paths.matcher(HttpMethod.POST, "/api/cases");
      http.csrf(csrf -> csrf.ignoringRequestMatchers(testLogin, createCase));
    }
    return http.authorizeHttpRequests(
            requests -> {
              if (testLoginEnabled) {
                requests.requestMatchers(testLogin).permitAll();
              }
              requests
                  .dispatcherTypeMatchers(DispatcherType.ERROR)
                  .permitAll()
                  .requestMatchers("/api/**")
                  .authenticated()
                  .anyRequest()
                  .denyAll();
            })
        .securityContext(context -> context.securityContextRepository(securityContextRepository))
        .exceptionHandling(
            errors ->
                errors
                    .authenticationEntryPoint(
                        (request, response, exception) ->
                            resolver.resolveException(
                                request,
                                response,
                                null,
                                new BusinessException(ErrorCode.UNAUTHORIZED)))
                    .accessDeniedHandler(
                        (request, response, exception) ->
                            resolver.resolveException(
                                request,
                                response,
                                null,
                                new BusinessException(ErrorCode.FORBIDDEN))))
        .build();
  }
}
