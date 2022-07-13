package com.example.classifier.predicates;

import com.example.classifier.dto.ErrorMessage;
import com.example.classifier.dto.SaveCountryDto;
import com.example.classifier.exceptions.MyValidationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CountryDtoPredicate implements Predicate<SaveCountryDto> {
    private final Environment env;

    public CountryDtoPredicate(Environment env) {
        this.env = env;
    }


    @Override
    public boolean test(SaveCountryDto saveCountryDto) {
        List<ErrorMessage> errorMessages = new ArrayList<>();

        String title = saveCountryDto.getTitle();//TITLE CHECK...

        int minTitleLength = Integer.parseInt(env.getProperty("minTitleLength"));
        int maxTitleLength = Integer.parseInt(env.getProperty("maxTitleLength"));

        if(title == null || title.length() < minTitleLength || title.length() > maxTitleLength){
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + --minTitleLength  +
                    " AND LESS THAN "  + maxTitleLength));
        }

        String description = saveCountryDto.getDescription();//DESCRIPTION CHECK...

        int minDescriptionLength = Integer.parseInt(env.getProperty("minDescriptionLength"));
        int maxDescriptionLength = Integer.parseInt(env.getProperty("maxDescriptionLength"));

        if(description == null || description.length() < minDescriptionLength || description.length() > maxDescriptionLength){
            errorMessages.add(new ErrorMessage("DESCRIPTION", "DESCRIPTION MUST BE GREATER THAN " + minDescriptionLength +
                    " AND LESS THAN " + maxDescriptionLength));
        }

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }
}
