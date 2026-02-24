package com.example.springai.controller;

import com.example.springai.ChatService;
import lombok.RequiredArgsConstructor;
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

	private final ChatService chatService;

	@GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> chat(@RequestParam String message) {
		return ResponseEntity.ok(chatService.chat(message));
	}

	@GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> chatStream(@RequestParam String message) {
		return chatService.chatStream(message);
	}

	@GetMapping(value = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> emailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
		return ResponseEntity.ok(chatService.emailResponse(customerName, customerMessage));
	}

	@GetMapping(value = "/prompt-stuffing", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> promptStuffing(@RequestParam String message) {
		return ResponseEntity.ok(chatService.promptStuffing(message));
	}
}
