package com.example.afisha.controllers.handler;

import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.ErrorMessage;
import com.example.afisha.dto.StructuredError;
import com.example.afisha.exceptions.MyValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.persistence.OptimisticLockException;

import java.net.ConnectException;
import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
        return new ErrorMessage(
                e.getLocalizedMessage()
        );
    }


    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage handle(ConnectException e){
        return new ErrorMessage(
                "CLASSIFIER SERVICE ISN'T RESPONDING"
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpRequestMethodNotSupportedException e){
        return new ErrorMessage(
                "CHECK URL"
        );
    }

    @ExceptionHandler(MyValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StructuredError handle(MyValidationException e){
        return new StructuredError(e.getErrorMessages());
    }
}
