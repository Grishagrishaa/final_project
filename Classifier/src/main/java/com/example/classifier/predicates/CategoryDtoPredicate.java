package com.example.classifier.predicates;

import com.example.classifier.dto.SaveConcertCategoryDto;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class CategoryDtoPredicate implements Predicate<SaveConcertCategoryDto> {
    @Override
    public boolean test(SaveConcertCategoryDto categoryDto) {
        return categoryDto.getTitle() != null;
    }
}
