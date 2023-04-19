package com.example.afisha.controllers.handler;

import com.example.afisha.dto.errors.ErrorMessage;
import com.example.afisha.dto.errors.StructuredError;
import com.example.afisha.exceptions.MyRoleNotFoundException;
import io.netty.handler.timeout.ReadTimeoutException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handle(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(ErrorMessage.builder()
                                                            .message(e.getMessage())
                                                            .build());
    }

    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ErrorMessage> handle(OptimisticLockException e){
        return ResponseEntity.status(409).body(ErrorMessage.builder()
                .message(e.getMessage())
                .build());
    }


    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorMessage> handle(ConnectException e){
        return ResponseEntity.internalServerError().body(ErrorMessage.builder()
                .message("CLASSIFIER SERVICE ISN'T RESPONDING")
                .build());
    }

    @ExceptionHandler(MyRoleNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(MyRoleNotFoundException e){
        return ResponseEntity.badRequest().body(ErrorMessage.builder()
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(UsernameNotFoundException e){
        return ResponseEntity.badRequest().body(ErrorMessage.builder()
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(ReadTimeoutException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage>handle(ReadTimeoutException e){
        return ResponseEntity.internalServerError().body(ErrorMessage.builder()
                .message("Connect Timed Out. Send request again")
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredError> handle(ConstraintViolationException e){
        return ResponseEntity.badRequest().body(StructuredError.builder()
                .errors(buildErrorMessages(e.getConstraintViolations()))
                .build());
    }

    private Set<ErrorMessage> buildErrorMessages(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(violation -> ErrorMessage.builder()
                        .field(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                .reduce((first, second) -> second)
                                .orElse(null)
                                .toString())
                        .message(violation.getMessage())
                        .build())
                .collect(toSet());
    }
}
