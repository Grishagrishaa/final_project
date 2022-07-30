package com.example.userservice.converters;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.dto.users.SaveUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SaveUserDtoToUserConverter implements Converter<SaveUserDto, User> {
    private final PasswordEncoder encoder;

    public SaveUserDtoToUserConverter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User convert(SaveUserDto source) {
        User user = new User();
        user.setNick(source.getNick());
        user.setMail(source.getMail());
        user.setPassword(encoder.encode(source.getPassword()));
        user.setRoles(source.getRoles());
        user.setStatus(source.getStatus());
        return user;
    }

    public User update(User user, SaveUserDto source) {
        if(source.getMail() != null) user.setMail(source.getMail());
        if(source.getNick() != null) user.setNick(source.getNick());
        if(source.getPassword() != null) user.setPassword(encoder.encode(source.getPassword()));
        if(source.getRoles() != null) user.setRoles(source.getRoles());
        if(source.getStatus() != null) user.setStatus(source.getStatus());
        return user;
    }
}