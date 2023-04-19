package com.example.userservice.controllers.handler;


import com.example.userservice.dto.errors.ErrorMessage;
import com.example.userservice.dto.errors.StructuredError;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

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

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorMessage> handle(PSQLException e){
    return ResponseEntity.badRequest().body(ErrorMessage.builder()
                .message(e.getServerErrorMessage().getDetail())
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

