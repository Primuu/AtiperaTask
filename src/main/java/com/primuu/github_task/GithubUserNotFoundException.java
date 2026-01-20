package com.primuu.github_task;

final class GithubUserNotFoundException extends RuntimeException {
    GithubUserNotFoundException(String message) {
        super(message);
    }
}
