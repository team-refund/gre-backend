package com.teamrefund.gre.config.policy;

import com.teamrefund.gre.service.policy.PolicyIngestionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration(proxyBeanMethods = false)
public class PolicyIngestionConfig {

  @Bean
  @ConditionalOnProperty(name = "gre.policy.ingestion.ebay-enabled", havingValue = "true")
  ApplicationRunner ingestEbayPolicy(PolicyIngestionService policyIngestionService) {
    return args ->
        policyIngestionService.ingestPolicyFromHtml(
            "eBay Money Back Guarantee policy",
            "EBAY",
            "ebay.com",
            new ClassPathResource("policy/ebay/money-back-guarantee.html"),
            "https://www.ebay.com/help/policies/ebay/ebay?id=4210",
            PolicyIngestionService.DEFAULT_SELECTOR,
            PolicyIngestionService.DEFAULT_CHUNK_SIZE,
            PolicyIngestionService.DEFAULT_MIN_CHUNK_SIZE_CHARS);
  }
}
