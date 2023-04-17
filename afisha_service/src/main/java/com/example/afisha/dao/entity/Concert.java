package com.example.afisha.dao.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "concerts", schema = "events")
@EntityListeners(AuditingEntityListener.class)
public class Concert extends BaseEvent {
    @Column(nullable = false)
    private UUID category;

    public Concert(String title, String description,
                   LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                   String type, String status,
                   String author,
                   UUID category) {
        super(title, description, eventDate, dateEndOfSale, type, status, author);
        this.category = category;
    }

    public Concert() {
    }


    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "category=" + category +
                '}';
    }
}
