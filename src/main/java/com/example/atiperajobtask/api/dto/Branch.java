package com.example.atiperajobtask.api.dto;

public record Branch(String name,
                     Commit commit) {

    public record Commit(String sha){

    }
}
