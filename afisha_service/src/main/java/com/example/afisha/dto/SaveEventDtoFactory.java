package com.example.afisha.dto;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.security.UserHolder;
import com.example.afisha.validation.validators.api.ValueOfEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.TypeMismatchException;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data @NoArgsConstructor
public class SaveEventDtoFactory {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String title;
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 1000)
    private String description;
    @NotNull
    private LocalDateTime eventDate;
    @NotNull
    private LocalDateTime dateEndOfSale;
    @NotNull
    @NotEmpty
    @ValueOfEnum(enumClass = EventType.class)
    private String type;
    @NotNull
    @NotEmpty
    @ValueOfEnum(enumClass = EventStatus.class)
    private String status;
    @NotNull
    private UUID country;
    @NotNull
    @Size(min = 1800)
    private Integer releaseYear;
    @NotNull
    private LocalDateTime releaseDate;
    @NotNull
    @Size(min = 1, max = 500)
    private Integer duration;
    @NotNull
    private UUID category;

    public BaseEvent getEntity(){
        if(EventType.FILM.equals(EventType.valueOf(type))){
            return new Film(
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    UserHolder.getUsername(),
                    country,
                    releaseYear, releaseDate,
                    duration);
        }else if(EventType.CONCERT.equals(EventType.valueOf(type))) {
            return new Concert(
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    UserHolder.getUsername(),
                    category);
        }
        throw new TypeMismatchException("INTERNAL SERVER ERROR | CANNOT RECOGNIZE TYPE OF EVENT");
    }
}
