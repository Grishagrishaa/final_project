package com.example.userservice.dto.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignDto {
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

    public String getMail() {
        return mail;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
}
