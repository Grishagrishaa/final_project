package com.example.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of") @NoArgsConstructor
public class JwtResponse {

    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean activated;
    private String jwtToken;
    private final String type = "Bearer";

}
