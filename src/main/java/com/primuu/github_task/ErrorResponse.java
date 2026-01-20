package com.primuu.github_task;

public record ErrorResponse(
        int status,
        String message
) {}
