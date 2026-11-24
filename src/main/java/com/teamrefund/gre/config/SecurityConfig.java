package com.teamrefund.gre.config;

import com.teamrefund.gre.dao.auth.TestLoginDao;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.servlet.DispatcherType;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration(proxyBeanMethods = false)
class SecurityConfig {
  private static final Set<String> DB_TEST_LOGINS = Set.of("test1", "test2", "test3", "test4");

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
    var createCase =
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/cases");
    if (testLoginEnabled) {
      http.csrf(csrf -> csrf.ignoringRequestMatchers(testLogin, createCase));
    }
    return http.authorizeHttpRequests(
            requests -> {
              if (testLoginEnabled) {
                requests.requestMatchers(testLogin).permitAll();
                requests.requestMatchers(createCase).authenticated();
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
        .httpBasic(
            basic ->
                basic
                    .authenticationEntryPoint(entryPoint)
                    .securityContextRepository(securityContextRepository))
        .build();
  }

  @Bean
  UserDetailsService gmailTestUsers(TestLoginDao testLoginDao) {
    return loginId -> {
      if (DB_TEST_LOGINS.contains(loginId)) {
        var passwordHash = testLoginDao.findActiveUserPasswordHash(loginId);
        if (passwordHash != null && passwordHash.startsWith("{bcrypt}")) {
          return User.withUsername(loginId).password(passwordHash).roles("GMAIL_TEST").build();
        }
      }
      throw new UsernameNotFoundException("Test account unavailable");
    };
  }
}
