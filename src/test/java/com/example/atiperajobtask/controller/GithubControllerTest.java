package com.example.atiperajobtask.controller;

import com.example.atiperajobtask.AtiperaJobTaskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        classes = {AtiperaJobTaskApplication.class},
        webEnvironment = WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(
        port = 0,
        stubs = "classpath:/stubs"
)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class GithubControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturn406WhenAcceptHeaderIsApplicationXml() {
        webTestClient.get()
                .uri("/repos/pawelklejka")
                .accept(MediaType.APPLICATION_XML)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("406 NOT_ACCEPTABLE")
                .jsonPath("$.message").isEqualTo("Wrong accept header. Should be application/json");
    }

    @Test
    void shouldReturn404WhenAcceptHeaderIsApplicationXml() {
        webTestClient.get()
                .uri("/repos/asdsaasd")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("404 NOT_FOUND")
                .jsonPath("$.message").isEqualTo("There is not such a user in github. Check if username is not misspelled");
    }

    @Test
    void shouldReturn200whenEverythingCorrect() {
        webTestClient.get()
                .uri("/repos/pawelklejka")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(
                        """
                        [
                            {
                                "repositoryName": "airlines-backend",
                                "ownerName": "pawelklejka",
                                "branches": [
                                  {
                                    "name": "dependabot/maven/com.itextpdf-itextpdf-5.5.13.3",
                                    "commit": {
                                      "sha": "e4c59d7e3518f0ad0a28bd61e72ba63097ed6ac3"
                                    }
                                  }
                                ]
                            }
                        ]
                        """
                );
    }
}
