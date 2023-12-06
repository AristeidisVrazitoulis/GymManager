package com.aris.gymmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> handleApiRequestException(NotFoundException e){
        // 1. Create a payload containing exception and details
        // 2. Return response entity
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorResponse apiException = new ErrorResponse(
                e.getMessage(),
                notFound.value(),
                new Date()
        );

        return new ResponseEntity<>(apiException, notFound);

    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorResponse apiException = new ErrorResponse(
                e.getMessage(),
                badRequest.value(),
                new Date()

        );

        return new ResponseEntity<>(apiException, badRequest );
    }

    @ExceptionHandler(value = {InvalidModelException.class})
    public ResponseEntity<ErrorResponse> handleInvalidModel(Exception e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorResponse apiException = new ErrorResponse(
                e.getMessage(),
                badRequest.value(),
                new Date()
        );

        return new ResponseEntity<>(apiException, badRequest );
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Customize the error response based on validation errors
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorResponse apiException = new ErrorResponse(
                ex.getMessage(),
                badRequest.value(),
                new Date()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }




}
