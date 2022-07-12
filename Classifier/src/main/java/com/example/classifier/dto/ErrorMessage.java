package com.example.classifier.dto;

public class ErrorMessage {
    private String exceptionType;
    private String message;

    public ErrorMessage(String exceptionType, String message) {
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
