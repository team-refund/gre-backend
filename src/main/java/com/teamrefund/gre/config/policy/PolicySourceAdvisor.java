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
    return request
        .mutate()
        .prompt(
            request
                .prompt()
                .augmentUserMessage(
                    request.prompt().getUserMessage().getText()
                        + "\n\n출처가 포함된 정책 문서:\n"
                        + documents))
        .build();
  }

  @Override
  public ChatClientResponse after(ChatClientResponse response, AdvisorChain chain) {
    return response;
  }
}
