package com.example.atiperajobtask.config.exception;

import com.example.atiperajobtask.exception.GithubRepoError;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Response.Body;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class MessageErrorDecoder implements ErrorDecoder {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()){
            case 404 -> new ResponseStatusException(HttpStatus.NOT_FOUND, GithubRepoError.USER_NOT_FOUND.getErrorInfo());
            case 415 -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, GithubRepoError.WRONG_ACCEPT_HEADER.getErrorInfo());
            default -> errorDecoder.decode(methodKey, response);
        };
    }

    public Optional<String> getMessage(Response response){
        Body body = response.body();
        if (body == null){
            return Optional.empty();
        }
        try (InputStream bodyInputStream = body.asInputStream()){
            byte[] bodyBytes = bodyInputStream.readAllBytes();
            return getMessage(bodyBytes);
        }catch (IOException e){
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private static Optional<String> getMessage(byte[] bodyBytes) throws IOException{
        try {
            Map<String, String> message = objectMapper.readValue(bodyBytes, new TypeReference<>(){});
            return Optional.ofNullable(message.get("message")).or(() -> Optional.ofNullable(message.get("error")));
        }catch (JsonParseException e){
            return Optional.of(new String(bodyBytes, StandardCharsets.UTF_8));
        }
    }
}
