package com.primuu.github_task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class GithubService {

    private final GithubClient client;

    GithubService(GithubClient client) {
        this.client = client;
    }

    List<RepoModelResponse> listNonForkUserRepositories(String username) {
        return client.getRepos(username).stream()
                .filter(repo -> !repo.fork())
                .map(this::toRepoResponse)
                .toList();
    }

    private RepoModelResponse toRepoResponse(GithubRepoModel repo) {
        String repoName = repo.name();
        String ownerLogin = repo.owner().login();

        List<BranchModelResponse> branches = fetchBranchResponses(ownerLogin, repoName);

        return new RepoModelResponse(repoName, ownerLogin, branches);
    }

    private List<BranchModelResponse> fetchBranchResponses(String ownerLogin, String repoName) {
        return client.getBranches(ownerLogin, repoName).stream()
                .map(this::toBranchResponse)
                .toList();
    }

    private BranchModelResponse toBranchResponse(GithubBranchModel branch) {
        return new BranchModelResponse(branch.name(), branch.commit().sha());
    }

}
