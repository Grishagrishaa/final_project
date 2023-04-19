package com.example.classifier.service.dto.errors;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class StructuredError {
    private final String logref = "structured error";
    private final Set<ErrorMessage> errors;
}