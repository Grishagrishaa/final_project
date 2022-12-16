package com.example.afisha.controllers.handler;

import com.example.afisha.dto.errors.ErrorMessage;
import com.example.afisha.dto.errors.StructuredError;
import com.example.afisha.exceptions.MyRoleNotFoundException;
import io.netty.handler.timeout.ReadTimeoutException;
import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;

import java.net.ConnectException;
import java.util.stream.Collectors;

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

    //TODO MYROLE EXCEPT, TYPENOTFOUNDEXC
    @ExceptionHandler(MyRoleNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(MyRoleNotFoundException e){
        return new ErrorMessage(
                e.getMessage()
        );
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(TypeMismatchException e){
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

    @ExceptionHandler(ReadTimeoutException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage handle(ReadTimeoutException e){
        return new ErrorMessage(
                "Connect Timed Out. Send request again"
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StructuredError handle(ConstraintViolationException e){
        return new StructuredError(e.getConstraintViolations().stream()
                .map(exc -> new ErrorMessage(
                        exc.getPropertyPath().toString().split("\\.")[2],
                        exc.getMessage()))
                .collect(Collectors.toSet()));
    }
}
