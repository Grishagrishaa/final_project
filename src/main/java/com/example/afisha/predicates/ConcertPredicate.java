package com.example.afisha.predicates;

import com.example.afisha.dto.SaveConcertDto;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 *
 *     private UUID country;
 *     private Integer releaseYear;
 *     private LocalDateTime releaseDate;
 *     private Integer duration;
 */

@Component
public class ConcertPredicate implements Predicate<SaveConcertDto> {
    @Override
    public boolean test(SaveConcertDto concert) {
        return concert.getTitle() != null && concert.getEventDate() != null &&
                concert.getType() != null && concert.getStatus() != null && concert.getCategory() != null;
    }
}
