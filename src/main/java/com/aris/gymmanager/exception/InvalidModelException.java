package com.aris.gymmanager.exception;

public class InvalidModelException extends RuntimeException{

    public InvalidModelException(String message) {
        super(message);
    }

    public InvalidModelException(String message, Throwable cause) {
        super(message, cause);
    }


}
