package com.aris.gymmanager.mvccontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionViewHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView handleException(Exception ex){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "An error occurred: "+ex.getMessage());
        return modelAndView;
    }
}
