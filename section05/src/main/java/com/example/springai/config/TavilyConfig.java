package com.example.springai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tavily")
@Getter
@Setter
public class TavilyConfig {
    private String searchUrl;
    private String apiKey;
    private int defaultResultLimit;
}
