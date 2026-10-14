package com.stayhapa.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException e) {
        String message = e.getReason() == null ? "Request failed" : e.getReason();
        return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", message, "type", e.getClass().getSimpleName()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.badRequest()
            .body(Map.of("error", e.getMessage(), "type", e.getClass().getSimpleName()));
    }
}
