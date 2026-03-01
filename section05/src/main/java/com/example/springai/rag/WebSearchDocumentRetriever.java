package com.example.springai.rag;

import com.example.springai.config.TavilyConfig;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@Slf4j
public class WebSearchDocumentRetriever implements DocumentRetriever {


    private final TavilyConfig tavilyConfig;
    private final RestClient restClient;
    private final int resultLimit;

    public WebSearchDocumentRetriever(TavilyConfig tavilyConfig, RestClient restClient, int resultLimit) {
        this.tavilyConfig = tavilyConfig;
        this.restClient = restClient;
        this.resultLimit = resultLimit;
        if (resultLimit <= 0) {
            throw new IllegalArgumentException("resultLimit must be greater than 0");
        }
    }

    @Override
    public List<Document> retrieve(Query query) {
        String apiKey = tavilyConfig.getApiKey();
        String baseUrl = tavilyConfig.getSearchUrl();
        Assert.hasText(baseUrl, "baseUrl must not be empty");
        Assert.hasText(apiKey, "apiKey must not be empty");

        String text = query.text();
        try {
            TavilyResponsePayload response = restClient
                    .post()
                    .uri(baseUrl)
                    .headers(httpHeaders -> {
                        httpHeaders.setBearerAuth(apiKey);
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .body(new TavilyRequestPayload(text, "advanced", resultLimit))
                    .retrieve()
                    .body(TavilyResponsePayload.class);
            if (Objects.isNull(response) || CollectionUtils.isEmpty(response.results())) {
                log.error("response is empty");
                return List.of();
            }

            return response.results().stream().map(hit -> Document.builder()
                    .text(hit.content())
                    .metadata("title", hit.title())
                    .metadata("url", hit.url())
                    .score(hit.score())
                    .build()).peek(document -> log.info("{}",document)).toList();
        } catch (Exception e) {
            log.error("Tavily search failed", e);
            return List.of();
        }

    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    record TavilyRequestPayload(String query, String searchDepth, int maxResult) {
    }

    record TavilyResponsePayload(List<Hit> results) {
        record Hit(String title, String url, String content, Double score) {
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private TavilyConfig tavilyConfig;
        private RestClient restClient;
        private Integer resultLimit;

        private Builder() {
        }

        public Builder withTavilyConfig(TavilyConfig tavilyConfig) {
            this.tavilyConfig = tavilyConfig;
            return this;
        }

        public Builder withRestClient(RestClient restClient) {
            this.restClient = restClient;
            return this;
        }

        public Builder withResultLimit(int resultLimit) {
            this.resultLimit = resultLimit;
            return this;
        }

        public WebSearchDocumentRetriever build() {
            Assert.notNull(tavilyConfig, "tavilyConfig must not be null");
            Assert.notNull(restClient, "restClient must not be null");

            int finalResultLimit = (Objects.nonNull(resultLimit) ? resultLimit : tavilyConfig.getDefaultResultLimit());
            return new WebSearchDocumentRetriever(tavilyConfig, restClient, finalResultLimit);
        }

    }
}
