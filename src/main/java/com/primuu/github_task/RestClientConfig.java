package com.primuu.github_task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
class RestClientConfig {

    private static final String GITHUB_ACCEPT = "application/vnd.github+json";
    private static final String GITHUB_API_VERSION_HEADER = "X-GitHub-Api-Version";

    @Bean
    RestClient restClient(RestClient.Builder builder,
                          @Value("${github.base-url}") String baseUrl,
                          @Value("${github.user-agent}") String userAgent,
                          @Value("${github.api-version}") String apiVersion) {
        return builder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, GITHUB_ACCEPT)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(GITHUB_API_VERSION_HEADER, apiVersion)
                .build();
    }

}
