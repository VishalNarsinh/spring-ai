package com.example.springai.service;

import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

public interface ChatService {
	String chat(String message, String model);

	Flux<String> chatStream(@RequestParam String message, @RequestParam String model);
}
