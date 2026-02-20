package com.example.springai.service.impl;

import com.example.springai.service.ChatPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("OPENAI")
@RequiredArgsConstructor
public class OpenAiChatPlatform implements ChatPlatform {

	private final ChatClient openAiChatClient;

	@Override
	public String chat(String prompt) {
		return openAiChatClient.prompt(prompt).call().content();
	}

	@Override
	public Flux<String> chatStream(String prompt) {
		return openAiChatClient.prompt(prompt).stream().content();
	}
}
