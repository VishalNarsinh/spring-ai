package com.example.springai.config;

import com.example.springai.advisors.TokenUsageLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ApplicationConfiguration {

	@Bean("chatClient")
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageLoggerAdvisor()))
				.defaultUser("How can you help me?")
				.build();
	}

	@Bean("chatMemoryChatClient")
	public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
		SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		return chatClientBuilder.defaultAdvisors(loggerAdvisor,memoryAdvisor).build();
	}

	@Bean
	public OpenAiChatOptions openAiChatOptions() {
		return OpenAiChatOptions.builder().model("gpt-5.2").maxCompletionTokens(300).temperature(0.7).build();
	}
}
