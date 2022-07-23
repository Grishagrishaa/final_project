package com.example.afisha.dao.entity;

import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "concerts", schema = "events")
@EntityListeners(AuditingEntityListener.class)
public class Concert extends Event {
    @Column(nullable = false)
    private UUID category;

    public Concert(String title, String description,
                   LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                   EventType type, EventStatus status,
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

}
