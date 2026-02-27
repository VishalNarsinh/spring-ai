package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatMemoryController {

	private final ChatClient chatClient;

	public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/chat-memory")
	public ResponseEntity<String> chatMemory(@RequestHeader("username") String username, @RequestParam("message") String message) {
		return ResponseEntity.ok(chatClient.prompt().user(message).advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID,username)).call().content());
	}
}
