package com.example.springai.advisors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class TokenUsageLoggerAdvisor implements CallAdvisor {


	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
		ChatResponse chatResponse = chatClientResponse.chatResponse();
		if (Objects.nonNull(chatResponse)) {
			Usage usage = chatResponse.getMetadata().getUsage();
			log.info("Token usage details: {}", usage.toString());
		}
		return chatClientResponse;
	}

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
