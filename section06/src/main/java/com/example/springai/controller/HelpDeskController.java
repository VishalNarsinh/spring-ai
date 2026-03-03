package com.example.springai.controller;

import com.example.springai.tools.HelpDeskTicketTool;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class HelpDeskController {

	private final ChatClient helpDeskChatClient;
	private final HelpDeskTicketTool helpDeskTicketTool;

	@GetMapping("/help-desk")
	public ResponseEntity<String> helpDesk(@RequestHeader String username, @RequestParam String message) {
		String modelResponse = helpDeskChatClient.prompt()
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
				.user(message)
				.tools(helpDeskTicketTool)
				.toolContext(Map.of("username", username))
				.call()
				.content();
		return ResponseEntity.ok(modelResponse);
	}
}
