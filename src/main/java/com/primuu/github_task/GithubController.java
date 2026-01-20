package com.primuu.github_task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
final class GithubController {

    private final GithubService service;

    GithubController(GithubService service) {
        this.service = service;
    }

    @GetMapping("/get-repos/{username}")
    List<RepoModelResponse> getUserRepos (@PathVariable String username) {
        return service.listNonForkRepos(username);
    }

}
