package com.haydenshui.stock.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleBusiness(Exception ex) {
        //TODO: template only
        throw new UnsupportedOperationException("Unimplemented method 'handleBusiness'");
    }
}
