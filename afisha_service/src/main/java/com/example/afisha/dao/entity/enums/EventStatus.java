package com.example.afisha.dao.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    DRAFT("Черновик"), PUBLISHED("Опубликовано");

    private final String status;

}
