package com.aris.gymmanager.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    public ResponseEntity<CustomerErrorResponse> handleApiRequestException(CustomerNotFoundException e){
        // 1. Create a payload containing exception and details
        // 2. Return response entity
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomerErrorResponse apiException = new CustomerErrorResponse(
                e.getMessage(),
                notFound.value(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, notFound);

    }


    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleException(Exception e){

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomerErrorResponse apiException = new CustomerErrorResponse(
                e.getMessage(),
                badRequest.value(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, badRequest );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Customize the error response based on validation errors
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        CustomerErrorResponse apiException = new CustomerErrorResponse(
                ex.getMessage(),
                badRequest.value(),
                ZonedDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }


    @ExceptionHandler(value = {PlanNotFoundException.class})
    public ResponseEntity<?> handlePlanException(PlanNotFoundException e){
        // 1. Create a payload containing exception and details
        // 2. Return response entity
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        CustomerErrorResponse apiException = new CustomerErrorResponse(
                e.getMessage(),
                notFound.value(),
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(apiException, notFound);

    }

}
