package com.example.afisha.dto;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.security.UserHolder;
import org.hibernate.TypeMismatchException;

import java.time.LocalDateTime;
import java.util.UUID;


public class SaveEventDtoFactory {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime dateEndOfSale;
    private EventType type;
    private EventStatus status;
    private UUID country;
    private Integer releaseYear;
    private LocalDateTime releaseDate;
    private Integer duration;
    private UUID category;

    public SaveEventDtoFactory() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getDateEndOfSale() {
        return dateEndOfSale;
    }

    public void setDateEndOfSale(LocalDateTime dateEndOfSale) {
        this.dateEndOfSale = dateEndOfSale;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public BaseEvent getEntity(){
        if(EventType.FILM.equals(type)){
            return new Film(
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    UserHolder.getUsername(),
                    country,
                    releaseYear, releaseDate,
                    duration);
        }else if(EventType.CONCERT.equals(type)) {
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
