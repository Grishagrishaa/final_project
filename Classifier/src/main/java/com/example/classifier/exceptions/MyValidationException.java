package com.example.classifier.exceptions;

import com.example.classifier.dto.ErrorMessage;

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
