package com.example.userservice.dto;

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
