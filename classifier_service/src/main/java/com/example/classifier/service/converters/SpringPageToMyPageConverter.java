package com.example.classifier.service.converters;

import com.example.classifier.service.dto.MyPage;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Component
public class SpringPageToMyPageConverter <T> implements Converter<Page<T>, MyPage<T>> {

    @Override
    public MyPage<T> convert(Page<T> source) {
        return MyPage.Builder.<T>create()
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
