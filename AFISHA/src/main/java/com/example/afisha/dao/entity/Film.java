package com.example.afisha.dao.entity;

import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "films", schema = "events")
public class Film extends Event {
    private UUID country;
    private Integer releaseYear;
    private LocalDateTime releaseDate;
    private Integer duration;


    public Film(LocalDateTime createDate,
                String title, String description,
                LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                EventType type, EventStatus status, UUID country,
                Integer releaseYear, LocalDateTime releaseDate,
                Integer duration) {
        super(createDate, title, description, eventDate, dateEndOfSale, type, status);
        this.country = country;
        this.releaseYear = releaseYear;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(SaveEventDtoFactory dto) {//MAPPER FROM DTO TO ENTITY
        super(LocalDateTime.now(), //<-- CREATE DATE
                dto.getTitle(), dto.getDescription(),
                dto.getEventDate(), dto.getDateEndOfSale(),
                dto.getType(), dto.getStatus());
        this.country = dto.getCountry();
        this.releaseYear = dto.getReleaseYear();
        this.releaseDate = dto.getReleaseDate();
        this.duration = dto.getDuration();
    }

    public Film() {
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

    @Override
    public String toString() {
        return "EventFilm{" +
                " uuid=" + getUuid() +
                ", createDate=" + getCreateDate() +
                ", updateDate=" + getUpdateDate() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", eventDate=" + getEventDate() +
                ", dateEndOfSale=" + getDateEndOfSale() +
                ", type=" + getType() +
                ", status=" + getStatus() +
                ", country=" + country +
                ", releaseYear=" + releaseYear +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }
}
