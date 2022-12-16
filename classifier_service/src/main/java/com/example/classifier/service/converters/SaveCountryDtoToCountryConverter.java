package com.example.classifier.service.converters;

import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.dto.SaveCountryDto;
import org.springframework.core.convert.converter.Converter;

public class SaveCountryDtoToCountryConverter implements Converter<SaveCountryDto, Country> {

    @Override
    public Country convert(SaveCountryDto source) {
        Country country = new Country();
        country.setTitle(source.getTitle());
        country.setDescription(source.getDescription());
        return country;
    }
}
