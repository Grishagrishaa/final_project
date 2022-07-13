package com.example.afisha.predicates;

import com.example.afisha.dao.entity.Film;
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
public class FilmPredicate implements Predicate<Film> {
    private final  Environment env;
    private final WebClient WebClient;
    private List<ErrorMessage> errorMessages;

    public FilmPredicate(Environment env, WebClient webClient) {
        this.env = env;
        WebClient = webClient;
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

    private void checkFutureDate(Integer year, String fieldName){

        if (year < LocalDateTime.now().getYear()) {
            errorMessages.add(new ErrorMessage(fieldName.toUpperCase(), fieldName + " CANNOT BE IN THE PAST YEAR"));
        }
    }


    private void checkCountry (UUID id){
        try {
            WebClient
                    .get()
                    .uri(String.join("", "/country/", id.toString()))
                    .retrieve().bodyToMono(CountryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            errorMessages.add(new ErrorMessage("COUNTRY", "COUNTRY WASN'T FOUND, CHECK URL"));
        }
    }

    private void checkDuration(Integer duration){
        int minDuration = Integer.parseInt(env.getProperty("minDuration"));
        int maxDuration = Integer.parseInt(env.getProperty("maxDuration"));

        if(duration == null || duration < minDuration || duration > maxDuration){
            errorMessages.add(new ErrorMessage("DURATION", "DURATION MUST BE GRETER " + minDuration +
                    " AND LESS THAN " + maxDuration));
        }
    }
}

