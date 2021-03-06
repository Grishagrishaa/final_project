package com.example.classifier.predicates;

import com.example.classifier.dto.ErrorMessage;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.dto.SaveCountryDto;
import com.example.classifier.exceptions.MyValidationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CategoryDtoPredicate implements Predicate<SaveConcertCategoryDto> {

    private final Environment env;
    private List<ErrorMessage> errorMessages;

    public CategoryDtoPredicate(Environment env) {
        this.env = env;
    }

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

        int minTitleLength = Integer.parseInt(env.getProperty("minTitleLength"));
        int maxTitleLength = Integer.parseInt(env.getProperty("maxTitleLength"));

        if(title == null || title.length() < minTitleLength || title.length() > maxTitleLength){
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + --minTitleLength  +
                    " AND LESS THAN "  + maxTitleLength));
        }
    }
}
