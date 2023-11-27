package com.aris.gymmanager.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class CustomerErrorResponse {
    private final String message;
    private final int status;
    private final ZonedDateTime timestamp;

    public CustomerErrorResponse(String message, int status, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }



    public int getHttpStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
