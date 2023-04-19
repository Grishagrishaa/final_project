package com.example.userservice.converters;

import com.example.userservice.controllers.pagination.MyPage;
import com.example.userservice.dao.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class SpringPageToMyPageConverter implements Converter<Page<User>, MyPage<User>> {

    @Override
    public MyPage<User> convert(Page<User> source) {
        return MyPage.<User>builder()
                .setContent(source.getContent())
                .setTotalElements(source.getTotalElements())
                .setNumberOfElements(source.getNumberOfElements())
                .setTotalPages(source.getTotalPages())
                .setSize(source.getSize())
                .setNumber(source.getNumber())
                .setFirst(source.isFirst())
                .setLast(source.isLast())
                .build();


    }
}
