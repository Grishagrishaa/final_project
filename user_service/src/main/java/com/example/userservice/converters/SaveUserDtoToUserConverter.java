package com.example.userservice.converters;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.dto.users.SaveUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class SaveUserDtoToUserConverter implements Converter<SaveUserDto, User> {
    private final PasswordEncoder encoder;

    @Override
    public User convert(SaveUserDto source) {
        User user = new User();
        user.setNick(source.getNick());
        user.setMail(source.getMail());
        user.setPassword(encoder.encode(source.getPassword()));
        user.setRoles(source.getRoles());
        user.setStatus(source.getStatus() == null ?
                EStatus.WAITING_ACTIVATION :
                EStatus.valueOf(source.getStatus()));
        return user;
    }
}
