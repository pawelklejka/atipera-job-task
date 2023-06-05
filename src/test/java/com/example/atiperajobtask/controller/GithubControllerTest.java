package com.example.atiperajobtask.controller;

import com.example.atiperajobtask.AtiperaJobTaskApplication;
import com.example.atiperajobtask.config.exception.ExceptionControllerAdvice;
import com.example.atiperajobtask.exception.GithubRepoError;
import com.example.atiperajobtask.model.GithubRepoProcessed;
import com.example.atiperajobtask.service.GithubService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = AtiperaJobTaskApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT
)
@AutoConfigureWireMock(port = 0)
public class GithubControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldReturn406WhenAcceptHeaderIsApplicationXml() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<byte[]> responseEntity = testRestTemplate.exchange(
                "/repos/pawelklejka",
                HttpMethod.GET,
                request,
                byte[].class
        );

        String response = new String(Objects.requireNonNull(responseEntity.getBody()), StandardCharsets.UTF_8);

        JSONAssert.assertEquals(
                """
                        {
                            "status": "406 NOT_ACCEPTABLE",
                            "message": "Wrong accept header. Should be application/json"
                        }
                        """,
                response,
                JSONCompareMode.LENIENT
        );
    }

    @Test
    void shouldReturn404WhenAcceptHeaderIsApplicationXml() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<byte[]> responseEntity = testRestTemplate.exchange(
                "/repos/jkasopdkapadszxc",
                HttpMethod.GET,
                request,
                byte[].class
        );

        String response = new String(Objects.requireNonNull(responseEntity.getBody()), StandardCharsets.UTF_8);

        JSONAssert.assertEquals(
                """
                        {
                            "status": "404 NOT_FOUND",
                            "message": "There is not such a user in github. Check if username is not misspelled"
                        }
                        """,
                response,
                JSONCompareMode.LENIENT
        );
    }
}
