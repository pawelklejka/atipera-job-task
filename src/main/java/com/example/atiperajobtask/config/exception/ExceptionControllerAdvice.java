package com.example.atiperajobtask.config.exception;

import com.example.atiperajobtask.exception.ErrorInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ErrorInfo> handleException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(new ErrorInfo(e.getStatusCode().toString(), e.getReason()));
    }

}
