package com.primuu.github_task;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
final class GithubController {

    private final GithubService service;

    GithubController(GithubService service) {
        this.service = service;
    }

    @GetMapping("/get-repos/{username}")
    List<RepoModelResponse> getUserRepos (@PathVariable String username) {
        return service.listNonForkUserRepositories(username);
    }

    @ExceptionHandler(GithubUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFoundException(GithubUserNotFoundException exception) {
        return new ErrorResponse(404, exception.getMessage());
    }

}
