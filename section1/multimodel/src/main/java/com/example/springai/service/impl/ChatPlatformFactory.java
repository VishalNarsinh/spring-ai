package com.example.springai.service.impl;

import com.example.springai.service.ChatPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatPlatformFactory {

	private final Map<String, ChatPlatform> platforms;

	public ChatPlatform getPlatform(String source) {

		ChatPlatform chatPlatform = platforms.get(source.toUpperCase());
		if(Objects.isNull(chatPlatform)) {
			throw new IllegalArgumentException("Unsupported model: " + source);
		}
		return chatPlatform;
	}
}
