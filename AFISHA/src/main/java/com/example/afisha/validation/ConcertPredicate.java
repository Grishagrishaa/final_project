package com.example.afisha.validation;

import com.example.afisha.controllers.EventController;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.uuidTest.ConcertCategoryTest;
import com.example.afisha.dto.errors.ErrorMessage;
import com.example.afisha.exceptions.MyValidationException;
import com.example.afisha.security.UserHolder;
import com.example.afisha.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

// В СЛУЧАЕ НЕВАЛИДНОГО ENUM - JSON PARSE ERROR(SINGLE)

@Component
public class ConcertPredicate implements Predicate<Concert> {
    @Value("${app.minTitleLength}")
    private Integer minTitleLength;
    @Value("${app.maxTitleLength}")
    private Integer maxTitleLength;
    @Value("${app.minDescriptionLength}")
    private Integer minDescriptionLength;
    @Value("${app.maxDescriptionLength}")
    private Integer maxDescriptionLength;
    @Value("${app.minAuthorLength}")
    private Integer minAuthorLength;
    @Value("${app.maxAuthorLength}")
    private Integer maxAuthorLength;

    private static final Logger log = LoggerFactory.getLogger(ConcertPredicate.class);
    private final WebClient WebClient;
    private List<ErrorMessage> errorMessages;
    private final UserHolder userHolder;

    public ConcertPredicate(WebClient webClient, UserHolder userHolder) {
        WebClient = webClient;
        this.userHolder = userHolder;
    }

    @Override
    public boolean test(Concert concert) {
        log.info("CHECK EVENT(CONCERT)");
        errorMessages = new ArrayList<>();

        log.info("CHECK TITLE");
        checkTitle(concert.getTitle());
        log.info("CHECK DESCRIPTION");
        checkDescription(concert.getDescription());
        log.info("CHECK EVENT_DATE");
        checkFutureDate(concert.getEventDate(), "EVENT_DATE");
        log.info("CHECK DATE_END_OF_SALE");
        checkFutureDate(concert.getDateEndOfSale(), "DATE_END_OF_SALE");
        log.info("CHECK CATEGORY");
        checkCategory(concert.getCategory());
        log.info("CHECK AUTHOR");
        checkAuthor(concert.getAuthor());


        if(!errorMessages.isEmpty()){
            log.info("MY_VALID_EXCEPTION");
            throw new MyValidationException(errorMessages);
        }

        log.info("SUCCESSFUL CHECK");
        return true;
    }

    public void testUpdate(Concert concert) {
        log.info("CHECK UPDATE EVENT(CONCERT)");
        errorMessages = new ArrayList<>();

        log.info("CHECK TITLE");
        if(concert.getTitle() != null) checkTitle(concert.getTitle());
        log.info("CHECK DESCRIPTION");
        if(concert.getDescription() != null) checkDescription(concert.getDescription());
        log.info("CHECK EVENT_DATE");
        if(concert.getEventDate() != null) checkFutureDate(concert.getEventDate(), "EVENT_DATE");
        log.info("CHECK DATE_END_OF_SALE");
        if(concert.getDateEndOfSale() != null) checkFutureDate(concert.getDateEndOfSale(), "DATE_END_OF_SALE");
        log.info("CHECK CATEGORY");
        if(concert.getCategory() != null) checkCategory(concert.getCategory());
        log.info("CHECK AUTHOR");
        if(concert.getAuthor() != null) checkAuthor(concert.getAuthor());


        if(!errorMessages.isEmpty()){
            log.info("MY_VALID_EXCEPTION");
            throw new MyValidationException(errorMessages);
        }
        log.info("SUCCESSFUL CHECK");
    }

    private void checkTitle(String title){

        if (title == null || title.length() < minTitleLength || title.length() > maxTitleLength) {
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + minTitleLength +
                    " AND LESS THAN " + maxTitleLength));
        }
    }

    private void checkDescription(String description){

        if (description == null || description.length() < minDescriptionLength || description.length() > maxDescriptionLength) {
            errorMessages.add(new ErrorMessage("DESCRIPTION", "DESCRIPTION MUST BE GREATER THAN " + minDescriptionLength +
                    " AND LESS THAN " + maxDescriptionLength));
        }
    }

    private void checkFutureDate(LocalDateTime date, String fieldName){
        if (date == null || !date.isAfter(LocalDateTime.now())) {
            errorMessages.add(new ErrorMessage(fieldName.toUpperCase(), fieldName.toUpperCase() + " MUST BE IN FUTURE"));
        }
    }

    private void checkCategory(UUID id) {
        try{
            WebClient
                    .get()
                    .uri(String.join("", "/concert/category/", id.toString()))
                    .header(AUTHORIZATION, ("Bearer ".concat(
                            JwtTokenUtil.generateAccessToken(userHolder.getUser().getUsername()))))
                    .retrieve().bodyToMono(ConcertCategoryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            errorMessages.add(new ErrorMessage("CATEGORY", "CATEGORY WASN'T FOUND, CHECK URL | " +
                    e.getMessage()));
        }
    }

    private void checkAuthor(String author){
        if(author == null || author.length() < minAuthorLength || author.length() > maxAuthorLength ){
            errorMessages.add(new ErrorMessage("AUTHOR", "AUTHOR MUST BE GREATER THAN " + minAuthorLength +
                    " AND LESS THAN " + maxAuthorLength));
        }
    }
}

