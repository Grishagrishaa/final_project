package com.example.userservice.converters;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.security.UserDetailsUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetailsUser> {

    @Override
    public UserDetailsUser convert(User user) {
        return UserDetailsUser.builder()
                .setUuid(user.getUuid())
                .setUsername(user.getNick())
                .setMail(user.getMail())
                .setPassword(user.getPassword())
                .setAuthorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList()))
                .setAccountNonExpired((LocalDateTime.now().getYear() - user.getUpdateDate().getYear()) < 10)
                .setAccountNonLocked(EStatus.ACTIVATED.equals(user.getStatus()))
                .setEnabled(EStatus.ACTIVATED.equals(user.getStatus()))
                .build();
    }
}
