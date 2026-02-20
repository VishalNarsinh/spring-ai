package com.example.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

	private final ChatClient client;

	@GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> chat(@RequestParam String message) {
		return ResponseEntity.ok(client.prompt().user(message).system("""
				You are an internal IT helpdesk assistant. Your role is to assist 
				employees with IT-related issues such as resetting passwords, 
				unlocking accounts, and answering questions related to IT policies.
				If a user requests help with anything outside of these 
				responsibilities, respond politely and inform them that you are 
				only able to assist with IT support tasks within your defined scope.
				""").call().content());
	}

	@GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> chatStream(@RequestParam String message) {
		return client.prompt(message).stream().content();
	}

}
