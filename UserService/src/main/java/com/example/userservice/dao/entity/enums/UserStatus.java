package com.example.userservice.dao.entity.enums;

public enum UserStatus {
    WAITING_ACTIVATION("НЕ АКТИВИРОВАН"),
    ACTIVATED("АКТИВИРОВАН"),
    DEACTIVATED("ДЕАКТИАИРОВАН");

    private final String status;

    UserStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
