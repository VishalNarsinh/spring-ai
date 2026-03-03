package com.example.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class TimeController {

	private final ChatClient timeChatClient;

	@GetMapping("/local-time")
	public ResponseEntity<String> localTime(@RequestHeader String username, @RequestParam String message) {
		return ResponseEntity.ok(
				timeChatClient.prompt().advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username)).user(message).call().content());
	}


}
