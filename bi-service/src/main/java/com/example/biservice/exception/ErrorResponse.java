package com.example.biservice.exception;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {

    private final int status;

    private final String error;

    private final String message;

    private List<ErrorField> errors;

    private final LocalDateTime timestamp;

    private final String path;

    public ErrorResponse(int status, String error, String message, List<ErrorField> errors, LocalDateTime timestamp, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
        this.path = path;
    }

    public ErrorResponse(int status, String error, String message, LocalDateTime timestamp, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }
}
