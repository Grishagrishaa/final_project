package com.example.afisha.predicates;

import com.example.afisha.dao.entity.enums.EventType;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component//в случае невалидниго урла выкинет ошибку
public class UrlTypePredicate implements Predicate<String> {
    @Override
    public boolean test(String type) {
        return (EventType.CONCERT.equals(EventType.valueOf(type.toUpperCase())))
                || (EventType.FILM.equals(EventType.valueOf(type.toUpperCase())));
    }

}
