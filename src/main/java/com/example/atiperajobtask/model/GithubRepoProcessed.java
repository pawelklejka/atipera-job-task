package com.example.atiperajobtask.model;

import com.example.atiperajobtask.api.dto.Branch;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GithubRepoProcessed {
    private String name;
    private String ownerName;
    private List<Branch> branches;
}
