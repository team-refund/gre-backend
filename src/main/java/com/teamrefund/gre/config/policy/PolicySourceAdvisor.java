package com.teamrefund.gre.config.policy;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

// QuestionAnswerAdvisor가 검색한 문서의 본문과 출처를 함께 전달한다.
public class PolicySourceAdvisor implements BaseAdvisor {

  @Override
  public int getOrder() {
    return 1;
  }

  @Override
  public ChatClientRequest before(ChatClientRequest request, AdvisorChain chain) {
    Object documents = request.context().get(QuestionAnswerAdvisor.RETRIEVED_DOCUMENTS);
    var prompt = request.prompt();
    String userMessage = prompt.getUserMessage().getText();
    String messageWithSources = userMessage + "\n\n청크 ID(id)와 출처가 포함된 정책 문서:\n" + documents;
    return request.mutate().prompt(prompt.augmentUserMessage(messageWithSources)).build();
  }

  @Override
  public ChatClientResponse after(ChatClientResponse response, AdvisorChain chain) {
    return response;
  }
}
