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

    public SaveUserDto() {//cant set private cause JACKSON REQUIRE public
    }

    public SaveUserDto(Builder builder) {
        this.nick = builder.nick;
        this.mail = builder.mail;
        this.password = builder.password;
        this.roles = builder.roles;
        this.status = builder.status;
    }

    public String getNick() {
        return nick;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public EStatus getStatus() {
        return status;
    }

    public static class Builder{
        private String nick;
        private String mail;
        private String password;
        private Set<Role> roles;
        private EStatus status;

        public Builder() {
        }

        public Builder setNick(String nick) {
            this.nick = nick;
            return this;
        }

        public Builder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder setStatus(EStatus status) {
            this.status = status;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }

        public SaveUserDto build(){
            return new SaveUserDto(this);
        }
    }
}
