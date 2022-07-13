package com.example.userservice.dto;

import com.example.userservice.dao.entity.enums.UserRole;
import com.example.userservice.dao.entity.enums.UserStatus;

public class SaveUserDto {
    private String mail;
    private String nick;
    private UserRole role;
    private UserStatus status;
    private String password;

    public SaveUserDto() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
