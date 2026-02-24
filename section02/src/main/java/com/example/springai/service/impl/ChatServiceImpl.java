package com.example.springai.service.impl;

import com.example.springai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatClient client;
	private final OpenAiChatOptions openAiChatOptions;

	@Value("classpath:/promptTemplates/email_response_prompt_template.st")
	Resource emailResponseTemplate;

	@Value("classpath:/promptTemplates/system_prompt_template.st")
	Resource systemPromptTemplate;

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

	@Override
	public String emailResponse(String customerName, String customerMessage) {
		return client.prompt()
				.system("""
						You are a professional customer service assistant which helps drafting email
						responses to improve the productivity of the customer support team
						""")
				.user(promptTemplateSpec -> promptTemplateSpec.text(emailResponseTemplate)
						.param("customerName", customerName)
						.param("customerMessage", customerMessage))
				.options(openAiChatOptions)
				.call()
				.content();
	}

	@Override
	public String promptStuffing(String prompt) {
		return client.prompt().system(systemPromptTemplate).user(prompt).options(openAiChatOptions).call().content();
	}

	@Override
	public String openAiChatOpenApi(String prompt) {
		return client.prompt(prompt).options(openAiChatOptions).call().content();
	}
}
