package com.primuu.github_task;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
final class GithubClient {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<GithubRepoModel>> REPOS =
            new ParameterizedTypeReference<>() {};

    GithubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    List<GithubRepoModel> getUserReposByUsername(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(REPOS);
        // TODO: error handling
    }

}
