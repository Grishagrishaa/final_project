package com.example.classifier.controlers;


import com.example.classifier.dto.ErrorMessage;
import com.example.classifier.dto.StructuredError;
import com.example.classifier.exceptions.MyValidationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
//TODO CONNECTION REFUSED EXCEPTION
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(IllegalArgumentException e){
        return new ErrorMessage(
                e.getMessage()
        );

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpRequestMethodNotSupportedException e){
        return new ErrorMessage(
                "CHECK URL"
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpMessageNotReadableException e){
        return new ErrorMessage(
                e.getMessage()
        );
    }


    @ExceptionHandler(MyValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StructuredError handle(MyValidationException e){
        return new StructuredError(
                e.getErrorMessages()
        );
    }
}
