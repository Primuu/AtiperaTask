package com.primuu.github_task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepoModel(
    String name,
    Owner owner,
    boolean fork
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {}
}
