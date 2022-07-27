package com.example.afisha.validation;

import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
public class OwnershipPredicate implements BiPredicate<String, String > {
    @Override
    public boolean test(String eventAuthor, String username) {
        if(!eventAuthor.equalsIgnoreCase(username)){
            throw new IllegalArgumentException("IT'S NOT YOUR EVENT, YOU CAN'T CHANGE IT");
        }

        return true;
    }
}
