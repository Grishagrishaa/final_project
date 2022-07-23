package com.example.userservice.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

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


    private UserDetailsUser(Builder builder) {
        this.uuid = builder.uuid;
        this.username = builder.username;
        this.mail = builder.mail;
        this.password = builder.password;
        this.authorities = builder.authorities;
        this.AccountNonExpired = builder.AccountNonExpired;
        this.AccountNonLocked = builder.AccountNonLocked;
        this.enabled = builder.enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getMail(){
        return mail;
    }

    public UUID getUuid() {
        return uuid;
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

    public static class Builder{
        private UUID uuid;
        private String username;
        private String mail;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private Boolean AccountNonExpired;
        private Boolean AccountNonLocked;
        private Boolean CredentialsNonExpired;
        private Boolean enabled;


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
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

        public Builder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder setAccountNonExpired(Boolean accountNonExpired) {
            AccountNonExpired = true;
            return this;
        }

        public Builder setAccountNonLocked(Boolean accountNonLocked) {
            AccountNonLocked = accountNonLocked;
            return this;
        }

        public Builder setCredentialsNonExpired(Boolean credentialsNonExpired) {
            CredentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder setEnabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public static Builder create(){
            return new Builder();
        }


        public UserDetailsUser build(){
            return new UserDetailsUser(this);
        }

    }
}
