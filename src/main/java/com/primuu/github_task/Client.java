package com.primuu.github_task;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
final class Client {

    private final RestClient restClient;

    private static final ParameterizedTypeReference<List<GithubRepoModel>> REPOS =
            new ParameterizedTypeReference<>() {};

    Client(RestClient restClient) {
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
