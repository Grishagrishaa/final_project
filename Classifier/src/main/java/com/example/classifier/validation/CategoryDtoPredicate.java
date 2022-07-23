package com.example.classifier.validation;

import com.example.classifier.dto.errors.ErrorMessage;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.exceptions.MyValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CategoryDtoPredicate implements Predicate<SaveConcertCategoryDto> {

    @Value("${app.minTitleLength}")
    private Integer MIN_TITLE_LENGTH;
    @Value("${app.maxTitleLength}")
    private Integer MAX_TITLE_LENGTH;
    private List<ErrorMessage> errorMessages;

    @Override
    public boolean test(SaveConcertCategoryDto categoryDto) {
        errorMessages = new ArrayList<>();

        checkTitle(categoryDto.getTitle());

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }

    private void checkTitle(String title){

        if(title == null || title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH){
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + --MIN_TITLE_LENGTH  +
                    " AND LESS THAN "  + MAX_TITLE_LENGTH));
        }
    }
}
