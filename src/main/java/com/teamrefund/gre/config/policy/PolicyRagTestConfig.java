package com.teamrefund.gre.config.policy;

import com.teamrefund.gre.controller.policy.PolicyRagTestController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration(proxyBeanMethods = false)
class PolicyRagTestConfig {

  @Bean
  @Order(1)
  @ConditionalOnProperty(name = "gre.policy.test.enabled", havingValue = "true")
  SecurityFilterChain policyRagTestSecurity(HttpSecurity http) throws Exception {
    PathPatternRequestMatcher testRoute =
        PathPatternRequestMatcher.withDefaults()
            .matcher(HttpMethod.GET, PolicyRagTestController.PATH);
    PathPatternRequestMatcher ingestionRoute =
        PathPatternRequestMatcher.withDefaults()
            .matcher(HttpMethod.POST, PolicyRagTestController.PATH + "/ingest");
    PathPatternRequestMatcher evaluationRoute =
        PathPatternRequestMatcher.withDefaults()
            .matcher(HttpMethod.POST, PolicyRagTestController.PATH + "/evaluate");
    PathPatternRequestMatcher batchEvaluationRoute =
        PathPatternRequestMatcher.withDefaults()
            .matcher(HttpMethod.POST, PolicyRagTestController.PATH + "/evaluate/batch");
    return http.securityMatcher(
            new OrRequestMatcher(testRoute, ingestionRoute, evaluationRoute, batchEvaluationRoute))
        .csrf(csrf -> csrf.ignoringRequestMatchers(ingestionRoute, evaluationRoute, batchEvaluationRoute))
        .authorizeHttpRequests(requests -> requests.anyRequest().hasRole("GMAIL_TEST"))
        .httpBasic(Customizer.withDefaults())
        .build();
  }
}
