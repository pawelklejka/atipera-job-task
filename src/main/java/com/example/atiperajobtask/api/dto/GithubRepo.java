package com.example.atiperajobtask.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record GithubRepo(@NotBlank boolean fork,
                         @NotBlank @JsonProperty("name") String name) {

}
