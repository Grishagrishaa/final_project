package com.example.afisha.dto;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Event;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dao.entity.utils.LocalDateTimeDeserializer;
import com.example.afisha.dao.entity.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.UUID;


public class SaveEventDtoFactory {
    private String title;
    private String description;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateEndOfSale;
    private EventType type;
    private EventStatus status;
    private UUID country;
    private Integer releaseYear;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime releaseDate;
    private Integer duration;
    private UUID category;

    public SaveEventDtoFactory(String title, String description,
                               LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                               EventType type, EventStatus status,
                               UUID country,
                               Integer releaseYear, LocalDateTime releaseDate,
                               Integer duration,
                               UUID category) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.dateEndOfSale = dateEndOfSale;
        this.type = type;
        this.status = status;
        this.country = country;
        this.releaseYear = releaseYear;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.category = category;
    }

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

    public Event getDto(){
        final IEvent event;
        if(EventType.FILM.equals(type)){
            return new Film(LocalDateTime.now(),//<--CreateDate
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    country,
                    releaseYear, releaseDate,
                    duration);
        }else {
            return new Concert(LocalDateTime.now(),//<--CreateDate
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    category);
        }//ДРУГОГО ТИПА БЫТЬ НЕ МОЖЕТ ПОТОМУ ЧТО ПРИ ПОПЫТКЕ ПЕРЕДАТЬ НЕВЕРНЫЙ ТИП ВЫБРОСИТЬСЯ JSON parse error
    }
}
