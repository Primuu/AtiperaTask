package com.primuu.github_task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record GithubBranchModel(
        String name,
        Commit commit
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    record Commit(String sha) {}
}
