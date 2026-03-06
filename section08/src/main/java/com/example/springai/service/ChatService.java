package com.example.springai.service;

import reactor.core.publisher.Flux;

public interface ChatService {

	String chat(String prompt);


	String promptStuffing(String prompt);

}
