package com.example.afisha.dao.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "films", schema = "events")
@EntityListeners(AuditingEntityListener.class)
public class Film extends BaseEvent {
    private UUID country;
    private Integer releaseYear;
    private LocalDateTime releaseDate;
    private Integer duration;


    public Film(String title, String description,
                LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                String type, String status,
                String author,
                UUID country,
                Integer releaseYear, LocalDateTime releaseDate,
                Integer duration) {
        super(title, description, eventDate, dateEndOfSale, type, status, author);
        this.country = country;
        this.releaseYear = releaseYear;
        this.releaseDate = releaseDate;
        this.duration = duration;
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
        return "Film{" +
                "country=" + country +
                ", releaseYear=" + releaseYear +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }
}
