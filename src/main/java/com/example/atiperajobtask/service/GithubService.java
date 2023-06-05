package com.example.atiperajobtask.service;

import com.example.atiperajobtask.api.GithubClient;
import com.example.atiperajobtask.api.dto.Branch;
import com.example.atiperajobtask.model.GithubRepoProcessed;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {
    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<GithubRepoProcessed> findAll(String userName, HttpHeaders headers){

        List<GithubRepoProcessed> githubRepos = githubClient.getGithubRepos(userName, headers).stream()
                .filter(r -> !r.fork())
                .map(r -> {
                    String[] ownerAndReponame = r.name().split("/");
                    String ownerOfRepo = ownerAndReponame[0];
                    String repoName = ownerAndReponame[1];

                    List<Branch> branches  = githubClient.getBranches(ownerOfRepo, repoName);

                    return GithubRepoProcessed.builder()
                            .name(repoName)
                            .ownerName(ownerOfRepo)
                            .branches(branches)
                            .build();
                })
                .collect(Collectors.toList());
        return githubRepos;
    }

}
