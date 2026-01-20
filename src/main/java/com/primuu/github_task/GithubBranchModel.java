package com.primuu.github_task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubBranchModel(
        String name,
        Commit commit
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Commit(String sha) {}
}
