package com.example.springai.controller;

import com.example.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;

	@GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> chat(@RequestParam String message) {
		return ResponseEntity.ok(chatService.chat(message));
	}

	@GetMapping(value = "/prompt-stuffing", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> promptStuffing(@RequestParam String message) {
		return ResponseEntity.ok(chatService.promptStuffing(message));
	}

}
