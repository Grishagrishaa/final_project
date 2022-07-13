package com.example.userservice.converters;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.ReadUserDto;
import com.example.userservice.pagination.MyPage;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserPageToReadUsersDtoMyPage implements Converter<Page<User>, MyPage<ReadUserDto>> {
    private final ModelMapper mapper;

    public UserPageToReadUsersDtoMyPage(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public MyPage<ReadUserDto> convert(Page<User> source) {
        MyPage<ReadUserDto> myPage = mapper.map(source, MyPage.class);

        myPage.setContent(toReadDto(source.getContent()));

        return myPage;
    }
    
    private List<ReadUserDto> toReadDto(List<User> source){
        return mapper.map(source, new TypeToken<List<ReadUserDto>>() {}.getType());
    }
}

