package com.example.atiperajobtask.controller;

import com.example.atiperajobtask.model.GithubRepoProcessed;
import com.example.atiperajobtask.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repos")
@RequiredArgsConstructor
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{userName}")
    public List<GithubRepoProcessed> findAll(@RequestHeader HttpHeaders headers, @PathVariable("userName") String userName){
        return githubService.findAll(userName, headers);
    }
}
