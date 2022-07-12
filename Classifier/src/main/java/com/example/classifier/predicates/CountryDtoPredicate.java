package com.example.classifier.predicates;

import com.example.classifier.dto.SaveCountryDto;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class CountryDtoPredicate implements Predicate<SaveCountryDto> {

    @Override
    public boolean test(SaveCountryDto saveCountryDto) {
        return saveCountryDto.getTitle() != null
                && saveCountryDto.getDescription() != null;
    }
}
