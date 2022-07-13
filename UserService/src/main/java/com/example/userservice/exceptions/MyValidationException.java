package com.example.userservice.exceptions;

import com.example.userservice.dto.ErrorMessage;

import java.util.List;

public class MyValidationException extends RuntimeException {
    private final List<ErrorMessage> errorMessages;

    public MyValidationException(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }



    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }
}
