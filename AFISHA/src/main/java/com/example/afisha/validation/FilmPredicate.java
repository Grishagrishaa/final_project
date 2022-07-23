package com.example.afisha.validation;

import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.uuidTest.CountryTest;
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
public class FilmPredicate implements Predicate<Film> {
    @Value("${app.minTitleLength}")
    private Integer minTitleLength;
    @Value("${app.maxTitleLength}")
    private Integer maxTitleLength;
    @Value("${app.minDescriptionLength}")
    private Integer minDescriptionLength;
    @Value("${app.maxDescriptionLength}")
    private Integer maxDescriptionLength;
    @Value("${app.minDuration}")
    private Integer minDuration;
    @Value("${app.minReleaseYear}")
    private Integer minReleaseYear;
    @Value("${app.maxDuration}")
    private Integer maxDuration;
    @Value("${app.minAuthorLength}")
    private Integer minAuthorLength;
    @Value("${app.maxAuthorLength}")
    private Integer maxAuthorLength;

    private final WebClient WebClient;
    private final UserHolder userHolder;
    private List<ErrorMessage> errorMessages;


    public FilmPredicate(WebClient webClient, UserHolder userHolder) {
        WebClient = webClient;
        this.userHolder = userHolder;
    }

    @Override
    public boolean test(Film film) {
        errorMessages = new ArrayList<>();

        checkTitle(film.getTitle());
        checkDescription(film.getDescription());
        checkFutureDate(film.getEventDate(), "EVENT_DATE");
        checkFutureDate(film.getDateEndOfSale(), "DATE_END_OF_SALE");
        checkCountry(film.getCountry());
        checkFutureDate(film.getReleaseYear(), "RELEASE_YEAR");
        checkFutureDate(film.getReleaseDate(), "RELEASE_DATE");
        checkDuration(film.getDuration());
        checkAuthor(film.getAuthor());

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

    private void checkFutureDate(Integer year, String fieldName){

        if (year == null || year < minReleaseYear) {
            errorMessages.add(new ErrorMessage(fieldName.toUpperCase(), fieldName + " CANNOT BE BEFORE " + minReleaseYear));
        }
    }


    private void checkCountry (UUID id){
        try {
            WebClient
                    .get()
                    .uri(String.join("", "/country/", id.toString()))
                    .header(AUTHORIZATION, ("Bearer ".concat(
                            JwtTokenUtil.generateAccessToken(userHolder.getUser().getUsername()))))

                    .retrieve().bodyToMono(CountryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            errorMessages.add(new ErrorMessage("COUNTRY", "COUNTRY WASN'T FOUND, CHECK URL"));
        }
    }

    private void checkDuration(Integer duration){

        if(duration == null || duration < minDuration || duration > maxDuration){
            errorMessages.add(new ErrorMessage("DURATION", "DURATION MUST BE GRETER " + minDuration +
                    " AND LESS THAN " + maxDuration));
        }
    }

    private void checkAuthor(String author){
        if(author == null || author.length() < minAuthorLength || author.length() > maxAuthorLength ){
            errorMessages.add(new ErrorMessage("AUTHOR", "AUTHOR MUST BE GREATER THAN " + minAuthorLength +
                    " AND LESS THAN " + maxAuthorLength));
        }
    }
}

