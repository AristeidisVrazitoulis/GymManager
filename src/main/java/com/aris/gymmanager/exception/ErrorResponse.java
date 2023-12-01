package com.aris.gymmanager.exception;

import java.util.Date;

public class ErrorResponse {
    private final String message;
    private final int status;
    private final Date timestamp;

    public ErrorResponse(String message, int status, Date timestamp) {
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

    public Date getTimestamp() {
        return timestamp;
    }
}
