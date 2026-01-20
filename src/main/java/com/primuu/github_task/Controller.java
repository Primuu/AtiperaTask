package com.primuu.github_task;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
public class Controller {

    private final RestClient restClient;

    public Controller(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
    }

    @GetMapping("/get-repos/{username}")
    public String getReposByUsername(@PathVariable String username) {
//    public List<GithubRepoModel> getUserReposByUsername(@PathVariable String username) {
        return restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .body(String.class);
    }

}
