package com.primuu.github_task;

import java.util.List;

public record RepoModelResponse(
        String repositoryName,
        String ownerLogin,
        List<BranchModelResponse> branches
) {}

record BranchModelResponse(String name,
                           String sha
) {}
