package com.example.userservice.dao.entity.enums;

public enum UserRole {
    ADMIN("АДМИНИСТРАТОР"),
    USER("ПОЛЬЗОВАТЕЛЬ");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
