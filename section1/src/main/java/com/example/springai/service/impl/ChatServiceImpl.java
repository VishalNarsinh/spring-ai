package com.example.springai.service.impl;

import com.example.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatPlatformFactory chatPlatformFactory;

	@Override
	public String chat(String message, String model) {
		return chatPlatformFactory.getPlatform(model).chat(message);
	}

	@Override
	public Flux<String> chatStream(String message, String model) {
		return chatPlatformFactory.getPlatform(model).chatStream(message);
	}
}
