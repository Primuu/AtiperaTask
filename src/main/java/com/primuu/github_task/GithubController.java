package com.primuu.github_task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
final class GithubController {

    private final GithubService service;

    GithubController(GithubService service) {
        this.service = service;
    }

    @GetMapping("/get-repos/{username}")
    List<RepoModelResponse> getUserRepos (@PathVariable String username) {
        return service.listNonForkRepos(username);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFoundException(HttpClientErrorException.NotFound exception) {
        return new ErrorResponse(404, "User with given username not found.");
    }

}
