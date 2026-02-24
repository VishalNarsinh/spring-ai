package com.example.springai.impl;

import com.example.springai.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatClient client;

	@Override
	public String chat(String prompt) {
		return client.prompt().user(prompt).system("""
				You are an internal IT helpdesk assistant. Your role is to assist
				employees with IT-related issues such as resetting passwords,
				unlocking accounts, and answering questions related to IT policies.
				If a user requests help with anything outside of these
				responsibilities, respond politely and inform them that you are
				only able to assist with IT support tasks within your defined scope.
				""").call().content();
	}

	@Override
	public Flux<String> chatStream(String prompt) {
		return client.prompt(prompt).stream().content();
	}
}
