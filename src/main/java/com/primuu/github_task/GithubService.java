package com.primuu.github_task;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
final class GithubService {

    private final GithubClient client;

    GithubService(GithubClient client) {
        this.client = client;
    }

    List<RepoModelResponse> listNonForkReposWithBranches(String username) {
        return client.getRepos(username).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    String repositoryName = repo.name();
                    String ownerLogin = repo.owner().login();

                    List<BranchModelResponse> branches = client.getBranches(ownerLogin, repositoryName).stream()
                            .map(branch -> new BranchModelResponse(
                                    branch.name(),
                                    branch.commit().sha()
                            ))
                            .toList();

                    return new RepoModelResponse(repositoryName, ownerLogin, branches);
                        })
                .toList();
    }

}
