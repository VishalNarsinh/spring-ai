package com.example.springai.controller;

import com.example.springai.model.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StructuredOutputController {

	private final ChatClient chatClient;

	public StructuredOutputController(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
	}

	@GetMapping("/chat-bean")
	public ResponseEntity<CountryCities> chatBean(@RequestParam String message) {
		return ResponseEntity.ok().body(chatClient.prompt(message).call()
//				.entity(CountryCities.class)
				.entity(new BeanOutputConverter<>(CountryCities.class)));
	}

	@GetMapping("/chat-list")
	public ResponseEntity<List<String>> chatList(@RequestParam String message) {
		return ResponseEntity.ok().body(chatClient.prompt(message).call().entity(new ListOutputConverter()));
	}

	@GetMapping("/chat-map")
	public ResponseEntity<Map<String, Object>> chatMap(@RequestParam String message) {
		return ResponseEntity.ok().body(chatClient.prompt(message).call().entity(new MapOutputConverter()));
	}

	@GetMapping("/chat-bean-list")
	public ResponseEntity<List<CountryCities>> chatBeanList(@RequestParam String message) {
		return ResponseEntity.ok().body(chatClient.prompt(message).call().entity(new ParameterizedTypeReference<List<CountryCities>>() {}));
	}
}

