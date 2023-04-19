package com.example.userservice.security;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

@Builder(setterPrefix = "set")
@Getter
public class UserDetailsUser implements UserDetails {

    private final UUID uuid;
    private final String username;
    private final String mail;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Boolean AccountNonExpired;
    private final Boolean AccountNonLocked;
    private final Boolean CredentialsNonExpired = true;
    private final Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return AccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return AccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return CredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
