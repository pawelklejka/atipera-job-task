package com.example.atiperajobtask.model;

import com.example.atiperajobtask.api.dto.Branch;

import java.util.List;

public record GithubRepoProcessed(String repositoryName, String ownerName, List<Branch> branches) {}
