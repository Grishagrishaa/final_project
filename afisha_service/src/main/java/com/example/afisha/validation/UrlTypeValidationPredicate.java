package com.example.afisha.validation;

import com.example.afisha.dao.entity.enums.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.example.afisha.dao.entity.enums.EventType.CONCERT;
import static com.example.afisha.dao.entity.enums.EventType.FILM;

@Component//в случае невалидниго урла выкинет ошибку
public class UrlTypeValidationPredicate implements Predicate<String> {

    public boolean test(String type) {
        try {
            boolean test = CONCERT.equals(EventType.valueOf(type)) || FILM.equals(EventType.valueOf(type));
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("PLEASE, PROVIDE IN URL SUPPORTED EVENT TYPE: " + Arrays.toString(EventType.values()));
        }
        return true;
    }
}
