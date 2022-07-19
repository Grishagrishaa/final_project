package com.example.userservice.converters;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SignDtoToSaveDtoConverter implements Converter<SignDto, SaveUserDto> {
    @Override
    public SaveUserDto convert(SignDto source) {
        SaveUserDto userDto = new SaveUserDto();
        userDto.setMail(source.getMail());
        userDto.setNick(source.getNick());
        userDto.setPassword(source.getPassword());
        userDto.setRoles(Set.of(new Role(1L, ERole.USER)));
        userDto.setStatus(EStatus.WAITING_ACTIVATION);
        return userDto;
    }
}
