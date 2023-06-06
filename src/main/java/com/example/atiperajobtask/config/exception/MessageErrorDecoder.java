package com.example.atiperajobtask.config.exception;

import com.example.atiperajobtask.exception.GithubRepoError;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Log4j2
public class MessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()){
            case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND, GithubRepoError.USER_NOT_FOUND.getErrorInfo());
            case 415 -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, GithubRepoError.WRONG_ACCEPT_HEADER.getErrorInfo());
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
