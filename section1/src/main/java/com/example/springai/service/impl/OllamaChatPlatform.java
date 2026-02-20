package com.example.springai.service.impl;

import com.example.springai.service.ChatPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("OLLAMA")
@RequiredArgsConstructor
public class OllamaChatPlatform implements ChatPlatform {

	private final ChatClient ollamaChatClient;

	@Override
	public String chat(String prompt) {
		return ollamaChatClient.prompt(prompt).call().content();
	}

	@Override
	public Flux<String> chatStream(String prompt) {
		return ollamaChatClient.prompt(prompt).stream().content();
	}
}
