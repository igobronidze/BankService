package com.egs.bankservice.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.egs.bankservice.exception.BankException;

@ControllerAdvice
public class CustomExceptionController {

    @ExceptionHandler(value = BankException.class)
    public ResponseEntity<Object> exception(BankException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
