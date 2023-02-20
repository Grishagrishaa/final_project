package com.example.userservice.converters;


import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class SignDtoToSaveUserDtoConverter implements Converter<SignDto, SaveUserDto> {
    @Override
    public SaveUserDto convert(SignDto source) {
        return SaveUserDto.Builder.create()
                .setMail(source.getMail())
                .setNick(source.getNick())
                .setPassword(source.getPassword())
                .setRoles(null)//DEFAULT VALUES WILL BE SET IN SERVICE LAYER
                .setStatus(null)//...
                .build();
    }
}
