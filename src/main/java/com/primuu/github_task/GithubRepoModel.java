package com.primuu.github_task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepoModel(
    String name,
//    String ownerLogin,
    boolean fork
) {}
