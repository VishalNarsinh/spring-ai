package com.example.springai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationConfiguration {

	@Bean("chatClient")
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.defaultAdvisors(List.of(new SimpleLoggerAdvisor())).build();
	}
}
