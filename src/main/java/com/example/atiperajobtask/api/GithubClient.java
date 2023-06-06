package com.example.atiperajobtask.api;


import com.example.atiperajobtask.api.dto.Branch;
import com.example.atiperajobtask.api.dto.GithubRepo;
import com.example.atiperajobtask.config.client.FeignClientConfig;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Service
@FeignClient(
        name = "github-api",
        url = "${api-client.github.url}",
        configuration = {FeignClientConfig.class})
@Headers("Accept: application/json")
public interface GithubClient {

    @GetMapping("/users/{userName}/repos")
    List<GithubRepo> getGithubRepos(@PathVariable("userName") String userName, @RequestHeader HttpHeaders headers);

    @GetMapping("/repos/{userName}/{repoName}/branches")
    List<Branch> getBranches(@PathVariable("userName") String userName, @PathVariable("repoName") String repoName);
}
