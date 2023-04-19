package com.example.afisha.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "films", schema = "events")
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor
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

}
