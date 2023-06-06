package com.example.atiperajobtask.service;

import com.example.atiperajobtask.api.dto.Branch;
import com.example.atiperajobtask.api.dto.GithubRepo;
import com.example.atiperajobtask.model.GithubRepoProcessed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;


public class GithubServiceTest {

    @Test
    public void createGithubRepo(){
        var githubRepo = new GithubRepoProcessed(
                "repoName",
                "ownerName",
                List.of(new Branch("repoBranch", new Branch.Commit("5ha5as5"))));

        var repoName = "repoName";
        var ownerName = "ownerName";
        var branchDTOS = List.of(new Branch("repoBranch", new Branch.Commit("5ha5as5")));

        Assertions.assertEquals(repoName, githubRepo.repositoryName());
        Assertions.assertEquals(ownerName, githubRepo.ownerName());
        Assertions.assertEquals(branchDTOS, githubRepo.branches());

    }
    @Test
    public void createGithubRepoDTO(){
        var githubRepo = new GithubRepo(false,"repo");

        var isFork = false;
        var repoName = "repo";

        Assertions.assertEquals(isFork, githubRepo.fork());
        Assertions.assertEquals(repoName, githubRepo.name());

    }
}
