package com.primuu.github_task;

public record RepoModelResponse(
        String repositoryName,
        String ownerLogin
        // TODO branches
) {}
