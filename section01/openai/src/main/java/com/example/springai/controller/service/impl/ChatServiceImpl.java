package com.example.springai.controller.service.impl;

import com.example.springai.controller.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatClient chatClient;

	@Override
	public String chat(String prompt) {
		return chatClient.prompt(prompt).call().content();
	}

	@Override
	public Flux<String> chatStream(String prompt) {
		return chatClient.prompt(prompt).stream().content();
	}
}
