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

    private static final ParameterizedTypeReference<List<GithubBranchModel>> BRANCHES =
            new ParameterizedTypeReference<>() {};

    GithubClient(RestClient restClient) {
        this.restClient = restClient;
    }

    List<GithubRepoModel> getRepos(String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(REPOS);
    }

    List<GithubBranchModel> getBranches(String owner, String repo) {
        return restClient.get()
                .uri("/repos/{owner}/{repo}/branches", owner, repo)
                .retrieve()
                .body(BRANCHES);
    }

}
