package com.example.afisha.dto;

import java.util.List;

public class StructuredError {
    private final String logref;
    private final List<ErrorMessage>  errorMessagesList;

    public StructuredError(List<ErrorMessage> errorMessagesList) {
        this.logref = "structured_error";
        this.errorMessagesList = errorMessagesList;
    }

    public String getLogref() {
        return logref;
    }

    public List<ErrorMessage> getErrorMessagesList() {
        return errorMessagesList;
    }
}
