package com.example.afisha.predicates;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.ConcertCategoryTest;
import com.example.afisha.dto.CountryTest;
import com.example.afisha.dto.ErrorMessage;
import com.example.afisha.exceptions.MyValidationException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

// В СЛУЧАЕ НЕВАЛИДНОГО ENUM - JSON PARSE ERROR(SINGLE)

@Component
public class ConcertPredicate implements Predicate<Concert> {
    private final  Environment env;
    private final WebClient WebClient;
    private List<ErrorMessage> errorMessages;

    public ConcertPredicate(Environment env, WebClient webClient) {
        this.env = env;
        WebClient = webClient;
    }

    @Override
    public boolean test(Concert concert) {
        errorMessages = new ArrayList<>();

        checkTitle(concert.getTitle());
        checkDescription(concert.getDescription());
        checkFutureDate(concert.getEventDate(), "EVENT_DATE");
        checkFutureDate(concert.getDateEndOfSale(), "DATE_END_OF_SALE");
        checkCategory(concert.getCategory());

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }

    private void checkTitle(String title){

        int minTitleLength = Integer.parseInt(env.getProperty("minTitleLength"));
        int maxTitleLength = Integer.parseInt(env.getProperty("maxTitleLength"));

        if (title == null || title.length() < minTitleLength || title.length() > maxTitleLength) {
            errorMessages.add(new ErrorMessage("TITLE", "TITLE MUST BE GREATER THAN " + minTitleLength +
                    " AND LESS THAN " + maxTitleLength));
        }
    }

    private void checkDescription(String description){

        int minDescriptionLength = Integer.parseInt(env.getProperty("minDescriptionLength"));
        int maxDescriptionLength = Integer.parseInt(env.getProperty("maxDescriptionLength"));

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
                    .retrieve().bodyToMono(ConcertCategoryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            errorMessages.add(new ErrorMessage("CATEGORY", "CATEGORY WASN'T FOUND, CHECK URL"));
        }

    }
}

