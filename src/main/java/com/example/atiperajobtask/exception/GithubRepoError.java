package com.example.atiperajobtask.exception;

public enum GithubRepoError {
    USER_NOT_FOUND("There is not such a user in github. Check if username is not misspelled"),
    WRONG_ACCEPT_HEADER("Wrong accept header. Should be application/json");

    private String errorInfo;

    GithubRepoError(String errorInfo){this.errorInfo = errorInfo; }

    public String getErrorInfo(){return errorInfo; }


    @Override
    public String toString() {
        return errorInfo;
    }
}
