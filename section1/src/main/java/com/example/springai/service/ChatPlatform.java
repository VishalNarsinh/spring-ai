package com.example.springai.service;

import reactor.core.publisher.Flux;

public interface ChatPlatform {
	String chat(String prompt);

	Flux<String> chatStream(String prompt);
}
