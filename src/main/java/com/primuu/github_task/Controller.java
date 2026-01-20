package com.primuu.github_task;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
public class Controller {

    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<GithubRepoModel>> REPOS =
            new ParameterizedTypeReference<>() {};

    public Controller(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/get-repos/{username}")
    public List<GithubRepoModel> getUserReposByUsername(@PathVariable String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(REPOS);
    }

}
