package com.example.atiperajobtask.service;

import com.example.atiperajobtask.api.GithubClient;
import com.example.atiperajobtask.api.dto.Branch;
import com.example.atiperajobtask.api.dto.GithubRepo;
import com.example.atiperajobtask.model.GithubRepoProcessed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;

import java.util.List;

@SpringBootTest
public class GithubServiceTest {

    @InjectMocks
    GithubService githubService;

    @Mock
    GithubClient githubClient;

    @Test
    public void createGithubRepo(){
        GithubRepoProcessed githubRepo = GithubRepoProcessed.builder()
                .name("repoName")
                .ownerName("ownerName")
                .branches(List.of(new Branch("repoBranch", new Branch.Commit("5ha5as5"))))
                .build();

        String repoName = "repoName";
        String ownerName = "ownerName";
        List<Branch> branchDTOS = List.of(new Branch("repoBranch", new Branch.Commit("5ha5as5")));

        Assertions.assertEquals(repoName, githubRepo.getName());
        Assertions.assertEquals(ownerName, githubRepo.getOwnerName());
        Assertions.assertEquals(branchDTOS, githubRepo.getBranches());

    }
    @Test
    public void createGithubRepoDTO(){
        GithubRepo githubRepo = new GithubRepo(false,"username/repo", "somelink");

        boolean isFork = false;
        String repoName = "username/repo";
        String url = "somelink";

        Assertions.assertEquals(isFork, githubRepo.fork());
        Assertions.assertEquals(repoName, githubRepo.name());
        Assertions.assertEquals(url, githubRepo.url());

    }
    @Test
    public void testGithubServiceFindAll(){
        List<GithubRepo> repos = List.of(new GithubRepo(false,"username/repo", "somelink"));
        List<Branch> branchDTOS = List.of(new Branch("branch", new Branch.Commit("5ha5as5")));

        List<GithubRepoProcessed> githubRepos = List.of(
                GithubRepoProcessed.builder()
                .name("repo")
                .ownerName("username")
                .branches(List.of(new Branch("branch", new Branch.Commit("5ha5as5"))))
                .build());

        Mockito.when(githubClient.getGithubRepos("username", new HttpHeaders()))
                .thenReturn(repos);

        Mockito.when(githubClient.getBranches("username", "repo"))
                .thenReturn(branchDTOS);

        List<GithubRepoProcessed> testRepo = githubService.findAll("username", new HttpHeaders());

        Assertions.assertEquals(githubRepos, testRepo);
    }
}
