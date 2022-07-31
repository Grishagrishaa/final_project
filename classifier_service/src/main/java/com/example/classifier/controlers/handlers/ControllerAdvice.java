package com.example.classifier.controlers.handlers;


import com.example.classifier.dto.errors.ErrorMessage;
import com.example.classifier.dto.errors.StructuredError;
import com.example.classifier.exceptions.MyValidationException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(UsernameNotFoundException e){
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

    @ExceptionHandler(ReadTimeoutException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage handle(ReadTimeoutException e){
        return new ErrorMessage(
                "Connect Timed Out. Send request again"
        );
    }
}
