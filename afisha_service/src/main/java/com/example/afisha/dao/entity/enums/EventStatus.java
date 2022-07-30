package com.example.afisha.dao.entity.enums;

public enum EventStatus {
    DRAFT("Черновик"),
    PUBLISHED("Опубликовано");

    private final String status;

    EventStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
