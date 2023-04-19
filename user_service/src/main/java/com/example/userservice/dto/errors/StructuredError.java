package com.example.userservice.dto.errors;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class StructuredError {
    private final String logref = "structured error";
    private final Set<ErrorMessage> errors;
}
