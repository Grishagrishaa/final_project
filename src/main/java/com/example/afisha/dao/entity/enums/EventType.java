package com.example.afisha.dao.entity.enums;

public enum EventType {
    FILM("Фильм"),
    CONCERT("Концерт ");

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String gettype() {
        return type;
    }

}