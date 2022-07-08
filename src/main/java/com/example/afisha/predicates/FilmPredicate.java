package com.example.afisha.predicates;

import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveFilmDto;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class FilmPredicate implements Predicate<SaveFilmDto> {
    @Override//TO VALIDATE FIELDS IN DTO
    public boolean test(SaveFilmDto film) {
        if (!film.getType().equals(EventType.FILM)) throw new IllegalArgumentException("INCORRECT / UNRECOGNIZED TYPE");
        return film.getTitle() != null && film.getEventDate() != null &&
                film.getType() != null && film.getStatus() != null &&
                film.getCountry() != null;

    }
}
