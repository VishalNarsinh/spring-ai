package com.example.springai.controller.service;

import reactor.core.publisher.Flux;

public interface ChatService {
	String chat(String prompt);

	Flux<String> chatStream(String prompt);
}
