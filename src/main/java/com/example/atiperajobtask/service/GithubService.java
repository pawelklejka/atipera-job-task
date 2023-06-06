package com.example.atiperajobtask.service;

import com.example.atiperajobtask.api.GithubClient;
import com.example.atiperajobtask.model.GithubRepoProcessed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public List<GithubRepoProcessed> findAll(String userName, HttpHeaders headers){
        return githubClient.getGithubRepos(userName, headers).stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    var repoName = repo.name();

                    var branches  = githubClient.getBranches(userName, repoName);

                    return new GithubRepoProcessed(repoName, userName, branches);
                })
                .collect(Collectors.toList());
    }

}
