package com.example.userservice.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class JwtResponse {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean activated;
    private String jwtToken;
    private final String type = "Bearer";


    public JwtResponse(String username, Collection<? extends GrantedAuthority> authorities, Boolean activated, String jwtToken) {
        this.username = username;
        this.authorities = authorities;
        this.activated = activated;
        this.jwtToken = jwtToken;
    }

    public JwtResponse() {
    }

    public static JwtResponse of(String username, Collection<? extends GrantedAuthority> authorities, Boolean activated,  String jwtToken){
        return new JwtResponse(username, authorities, activated, jwtToken);
    }

    public String getUsername() {
        return username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Boolean getActivated() {
        return activated;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public String getType() {
        return type;
    }
}
