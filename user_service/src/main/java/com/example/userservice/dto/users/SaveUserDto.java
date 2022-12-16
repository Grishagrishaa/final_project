package com.example.userservice.dto.users;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.service.validators.api.ValueOfEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class SaveUserDto {
    @NotNull
    @NotBlank
    @Size(min = 2, max = 10)
    private String nick;
    @Email
    private String mail;
    @NotNull
    @NotBlank
    @Size(min = 2, max = 15)
    private String password;
    private Set<Role> roles;
    @ValueOfEnum(enumClass = EStatus.class, message = "Incorrect status")
    private String status;

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

    public String getStatus() {
        return status;
    }

    public static class Builder{
        private String nick;
        private String mail;
        private String password;
        private Set<Role> roles;
        private String status;

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

        public Builder setStatus(String status) {
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
