package com.java.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*@ControllerAdvice*/
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handerException(Exception ex){
        return "redirect:/pages/error/exception.jsp";
    }
}
