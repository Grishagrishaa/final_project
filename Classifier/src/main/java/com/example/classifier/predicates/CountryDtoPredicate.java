package com.example.classifier.predicates;

import com.example.classifier.dto.ErrorMessage;
import com.example.classifier.dto.SaveCountryDto;
import com.example.classifier.exceptions.MyValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CountryDtoPredicate implements Predicate<SaveCountryDto> {
    @Value("${app.minTitleLength}")
    private Integer MIN_TITLE_LENGTH;
    @Value("${app.maxTitleLength}")
    private Integer MAX_TITLE_LENGTH;
    @Value("${app.minDescriptionLength}")
    private Integer MIN_DESCRIPTION_LENGTH;
    @Value("${app.maxDescriptionLength}")
    private Integer MAX_DESCRIPTION_LENGTH;
    private List<ErrorMessage> errorMessages;

    @Override
    public boolean test(SaveCountryDto saveCountryDto) {
        errorMessages = new ArrayList<>();

        checkTitle(saveCountryDto.getTitle());
        checkDescription(saveCountryDto.getDescription());

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

    private void checkDescription(String description){

        if(description == null || description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH){
            errorMessages.add(new ErrorMessage("DESCRIPTION", "DESCRIPTION MUST BE GREATER THAN " + MIN_DESCRIPTION_LENGTH +
                    " AND LESS THAN " + MAX_DESCRIPTION_LENGTH));
        }
    }
}

