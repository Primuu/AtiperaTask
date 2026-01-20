package com.primuu.github_task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record GithubRepoModel(
    String name,
    Owner owner,
    boolean fork
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    record Owner(String login) {}
}
