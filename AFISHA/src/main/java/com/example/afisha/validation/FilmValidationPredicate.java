package com.example.afisha.validation;

import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.uuidTest.CountryTest;
import com.example.afisha.dto.errors.ErrorMessage;
import com.example.afisha.exceptions.MyValidationException;
import com.example.afisha.security.UserHolder;
import com.example.afisha.security.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
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
public class FilmValidationPredicate implements Predicate<Film> {
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

    private static final Logger log = LoggerFactory.getLogger(FilmValidationPredicate.class);
    private final WebClient WebClient;
    private final UserHolder userHolder;
    private List<ErrorMessage> errorMessages;


    public FilmValidationPredicate(WebClient webClient, UserHolder userHolder) {
        WebClient = webClient;
        this.userHolder = userHolder;
    }

    @Override
    public boolean test(Film film) {
        log.info("CHECK EVENT(FILM)");
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
            log.info("MY_VALID_EXCEPTION");
            throw new MyValidationException(errorMessages);
        }

        log.info("SUCCESSFUL CHECK");
        return true;
    }

    public void testUpdate(Film film) {
        log.info("CHECK UPDATE EVENT(FILM)");
        errorMessages = new ArrayList<>();

        if(film.getTitle() != null) checkTitle(film.getTitle());
        if(film.getDescription() != null) checkDescription(film.getDescription());
        if(film.getEventDate() != null) checkFutureDate(film.getEventDate(), "EVENT_DATE");
        if(film.getDateEndOfSale() != null) checkFutureDate(film.getDateEndOfSale(), "DATE_END_OF_SALE");
        if(film.getCountry() != null) checkCountry(film.getCountry());
        if(film.getReleaseYear() != null) checkFutureDate(film.getReleaseYear(), "RELEASE_YEAR");
        if(film.getReleaseDate() != null) checkFutureDate(film.getReleaseDate(), "RELEASE_DATE");
        if(film.getDuration() != null) checkDuration(film.getDuration());
        if(film.getAuthor() != null) checkAuthor(film.getAuthor());

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

