package com.primuu.github_task;


import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWireMock({
        @ConfigureWireMock(baseUrlProperties = "github.base-url")
})
class GithubControllerIT {

    private static final String EXISTING_USER = "existing-user";
    private static final String NON_EXISTING_USER = "non-existing-user";
    private static final String REAL_REPO = "real-repo";
    private static final String FORKED_REPO = "forked-repo";

    @Autowired
    MockMvc mockMvc;

    @InjectWireMock
    WireMockServer wireMock;

    @BeforeEach
    void reset() {
        wireMock.resetAll();
    }

    @Test
    void returnsOnlyNonForkReposWithBranchesAndLastCommitSha() throws Exception {
        wireMock.stubFor(get(urlEqualTo("/users/" + EXISTING_USER + "/repos"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("github/users_existing-user_repos.json")));

        wireMock.stubFor(get(urlEqualTo("/repos/" + EXISTING_USER + "/" + REAL_REPO + "/branches"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("github/repos_existing-user_real-repo_branches.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/get-repos/" + EXISTING_USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))

                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].repositoryName").value(REAL_REPO))
                .andExpect(jsonPath("$[0].ownerLogin").value(EXISTING_USER))

                .andExpect(jsonPath("$[0].branches.length()").value(2))
                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[0].branches[1].name").value("dev"))
                .andExpect(jsonPath("$[0].branches[0].lastCommitSha").value("aaa111"))
                .andExpect(jsonPath("$[0].branches[1].lastCommitSha").value("bbb222"));

        wireMock.verify(0, getRequestedFor(urlEqualTo("/repos/" + EXISTING_USER + "/" + FORKED_REPO + "/branches")));
        wireMock.verify(1, getRequestedFor(urlEqualTo("/users/" + EXISTING_USER + "/repos")));
        wireMock.verify(1, getRequestedFor(urlEqualTo("/repos/" + EXISTING_USER + "/" + REAL_REPO + "/branches")));
    }

    @Test
    void returns404WithRequiredBodyWhenUserDoesNotExist() throws Exception {
        wireMock.stubFor(get(urlEqualTo("/users/" + NON_EXISTING_USER + "/repos"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("github/users_non-existing-user_repos_404.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/get-repos/" + NON_EXISTING_USER))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

}
