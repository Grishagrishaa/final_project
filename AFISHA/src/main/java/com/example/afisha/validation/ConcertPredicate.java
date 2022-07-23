package com.example.afisha.validation;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.uuidTest.ConcertCategoryTest;
import com.example.afisha.dto.errors.ErrorMessage;
import com.example.afisha.exceptions.MyValidationException;
import com.example.afisha.security.UserHolder;
import com.example.afisha.security.utils.JwtTokenUtil;
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


    private final WebClient WebClient;
    private List<ErrorMessage> errorMessages;
    private final UserHolder userHolder;

    public ConcertPredicate(WebClient webClient, UserHolder userHolder) {
        WebClient = webClient;
        this.userHolder = userHolder;
    }

    @Override
    public boolean test(Concert concert) {
        errorMessages = new ArrayList<>();

        checkTitle(concert.getTitle());
        checkDescription(concert.getDescription());
        checkFutureDate(concert.getEventDate(), "EVENT_DATE");
        checkFutureDate(concert.getDateEndOfSale(), "DATE_END_OF_SALE");
        checkCategory(concert.getCategory());
        checkAuthor(concert.getAuthor());


        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
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

