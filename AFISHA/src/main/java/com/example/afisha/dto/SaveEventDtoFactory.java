package com.example.afisha.dto;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.BaseEvent;
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
    private String author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "SaveEventDtoFactory{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", dateEndOfSale=" + dateEndOfSale +
                ", type=" + type +
                ", status=" + status +
                ", country=" + country +
                ", releaseYear=" + releaseYear +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", category=" + category +
                ", author='" + author + '\'' +
                '}';
    }

    public BaseEvent getEntity(){
        final IEvent event;
        if(EventType.FILM.equals(type)){
            return new Film(
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    author,
                    country,
                    releaseYear, releaseDate,
                    duration);
        }else {
            return new Concert(
                    title, description,
                    eventDate, dateEndOfSale,
                    type, status,
                    author,
                    category);
        }//ДРУГОГО ТИПА БЫТЬ НЕ МОЖЕТ ПОТОМУ ЧТО ПРИ ПОПЫТКЕ ПЕРЕДАТЬ НЕВЕРНЫЙ ТИП ВЫБРОСИТЬСЯ JSON parse error//exception
    }
}
