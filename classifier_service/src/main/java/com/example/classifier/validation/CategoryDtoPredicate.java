package com.example.classifier.validation;

import com.example.classifier.dto.errors.ErrorMessage;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.exceptions.MyValidationException;
import com.example.classifier.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class CategoryDtoPredicate implements Predicate<SaveConcertCategoryDto> {
    private static final Logger log = LoggerFactory.getLogger(CategoryDtoPredicate.class);

    @Value("${app.minTitleLength}")
    private Integer MIN_TITLE_LENGTH;
    @Value("${app.maxTitleLength}")
    private Integer MAX_TITLE_LENGTH;
    private List<ErrorMessage> errorMessages;

    @Override
    public boolean test(SaveConcertCategoryDto categoryDto) {
        log.info("CHECK_CATEGORY");
        errorMessages = new ArrayList<>();

        log.info("CHECK_TITLE");
        checkTitle(categoryDto.getTitle());

        if(!errorMessages.isEmpty()){
            log.info("MY_VALID_EXCEPTION");
            throw new MyValidationException(errorMessages);
        }

        log.info("SUCCESSFUL CHECK");
        return true;
    }

    private void checkTitle(String title){

        if(title == null || title.length() < MIN_TITLE_LENGTH || title.length() > MAX_TITLE_LENGTH){
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + --MIN_TITLE_LENGTH  +
                    " AND LESS THAN "  + MAX_TITLE_LENGTH));
        }
    }
}
