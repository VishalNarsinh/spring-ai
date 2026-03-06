package com.example.springai.service.impl;

import com.example.springai.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

	private final ChatClient client;

	@Value("classpath:/promptTemplates/hrPolicy.st")
	Resource systemPromptTemplate;

	public ChatServiceImpl(@Qualifier("chatClient") ChatClient client) {
		this.client = client;
	}

	@Override
	public String chat(String prompt) {
		return client.prompt().user(prompt).call().content();
	}

	@Override
	public String promptStuffing(String prompt) {
		return client.prompt().system(systemPromptTemplate).user(prompt).call().content();
	}

}
