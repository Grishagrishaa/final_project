package com.example.userservice.dto.users;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.enums.EStatus;

import java.util.Set;

public class SaveUserDto {
    private String nick;
    private String mail;
    private String password;
    private Set<Role> roles;
    private EStatus status;


    public String getNick() {
        return nick;
    }

    public SaveUserDto setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public SaveUserDto setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SaveUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public SaveUserDto setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public EStatus getStatus() {
        return status;
    }

    public SaveUserDto setStatus(EStatus status) {
        this.status = status;
        return this;
    }
}
