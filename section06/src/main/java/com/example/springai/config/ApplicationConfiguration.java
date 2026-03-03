package com.example.springai.config;

import com.example.springai.advisors.TokenUsageLoggerAdvisor;
import com.example.springai.tools.TimeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class ApplicationConfiguration {


	@Value("classpath:/promptTemplates/help_desk_system_prompt_template.st")
	private Resource helpDeskSystemPromptTemplate;

	@Bean("chatClient")
	public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
		return chatClientBuilder.defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageLoggerAdvisor()))
				.defaultSystem(helpDeskSystemPromptTemplate)
				.defaultUser("How can you help me?")
				.build();
	}

	@Bean
	public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
		return MessageWindowChatMemory.builder().maxMessages(4).chatMemoryRepository(jdbcChatMemoryRepository).build();
	}

	@Bean("chatMemoryChatClient")
	public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
		SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		return chatClientBuilder.defaultAdvisors(loggerAdvisor, memoryAdvisor).build();
	}

	@Bean("timeChatClient")
	public ChatClient timeChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, TimeTool timeTool) {
		SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
		TokenUsageLoggerAdvisor tokenUsageLoggerAdvisor = new TokenUsageLoggerAdvisor();
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		return chatClientBuilder.defaultTools(timeTool).defaultAdvisors(loggerAdvisor, memoryAdvisor, tokenUsageLoggerAdvisor).build();
	}

	@Bean("helpDeskChatClient")
	public ChatClient helpDeskChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, TimeTool timeTool) {
		SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
		TokenUsageLoggerAdvisor tokenUsageLoggerAdvisor = new TokenUsageLoggerAdvisor();
		MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
		return chatClientBuilder.defaultTools(timeTool).defaultAdvisors(loggerAdvisor, memoryAdvisor, tokenUsageLoggerAdvisor).build();
	}

	/*@Bean
	ToolExecutionExceptionProcessor toolExecutionExceptionProcessor(){
//		setting true will send the exception to client
		return new DefaultToolExecutionExceptionProcessor(true);
	}*/

	@Bean
	public OpenAiChatOptions openAiChatOptions() {
		return OpenAiChatOptions.builder().model("gpt-5.2").maxCompletionTokens(300).temperature(0.7).build();
	}
}
