package com.primuu.github_task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class GithubService {

    private final GithubClient client;

    GithubService(GithubClient client) {
        this.client = client;
    }

    List<RepoModelResponse> listNonForkRepos (String username) {
        return client.getUserReposByUsername(username).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> new RepoModelResponse(
                        repo.name(),
                        repo.owner().login()
                ))
                .toList();
    }

}
