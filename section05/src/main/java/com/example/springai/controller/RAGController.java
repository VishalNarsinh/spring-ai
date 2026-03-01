package com.example.springai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RAGController {

    private final QdrantVectorStore vectorStore;
    private final ChatClient chatMemoryChatClient;
    private final ChatClient webSearchRagChatClientConfig;

    @Value("classpath:promptTemplates/system_prompt_random_data_template.st")
    private Resource randomDataPromptTemplate;

    @Value("classpath:promptTemplates/system_prompt_template.st")
    private Resource hrSystemPromptTemplate;

    public RAGController(QdrantVectorStore vectorStore, ChatClient chatMemoryChatClient, ChatClient webSearchRagChatClientConfig) {
        this.vectorStore = vectorStore;
        this.chatMemoryChatClient = chatMemoryChatClient;
        this.webSearchRagChatClientConfig = webSearchRagChatClientConfig;
    }

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestHeader String username, @RequestParam String message) {
		/*SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
		List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));*/
        String response = chatMemoryChatClient.prompt()
                /*.system(promptSystemSpec -> promptSystemSpec.text(randomDataPromptTemplate).param("documents", similarContext))*/
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/document/chat")
    public ResponseEntity<String> documentChat(@RequestHeader String username, @RequestParam String message) {
	/*	SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
		List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
		String similarContext = similarDocs.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));*/
        String response = chatMemoryChatClient.prompt()
                /*.system(promptSystemSpec -> promptSystemSpec.text(hrSystemPromptTemplate).param("documents", similarContext))*/
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/web-search/chat")
    public ResponseEntity<String> webSearchChat(@RequestHeader String username, @RequestParam String message) {
        return ResponseEntity.ok(webSearchRagChatClientConfig
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec
                        .param(ChatMemory.CONVERSATION_ID, username)
                ).call()
                .content()
        );
    }
}
