package com.example.userservice.controllers;


import com.example.userservice.dto.errors.ErrorMessage;
import com.example.userservice.dto.MyValidationException;
import com.example.userservice.dto.errors.StructuredError;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(IllegalArgumentException e){
        return new ErrorMessage(
                e.getMessage()
        );

    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handle(OptimisticLockException e){
        return new ErrorMessage(
                e.getMessage()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpMessageNotReadableException e){
        return new ErrorMessage(//todo
                e.getLocalizedMessage()
        );
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpRequestMethodNotSupportedException e){
        return new ErrorMessage(
                "CHECK URL"
        );
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(PSQLException e){
        return new ErrorMessage(e.getServerErrorMessage().getDetail());
    }

    @ExceptionHandler(MyValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StructuredError handle(MyValidationException e){
        return new StructuredError(e.getErrorMessages());
    }

}

