package com.example.afisha.predicates;

import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class EventPredicate implements Predicate<SaveEventDtoFactory> {
    @Override
    public boolean test(SaveEventDtoFactory dtoFactory) {
        return dtoFactory.getTitle() != null && dtoFactory.getEventDate() != null &&
                dtoFactory.getType() != null && dtoFactory.getStatus() != null &&
                (dtoFactory.getCountry() != null || dtoFactory.getCategory() != null);

    }
}
