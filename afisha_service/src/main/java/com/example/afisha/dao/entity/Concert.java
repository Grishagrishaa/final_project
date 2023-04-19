package com.example.afisha.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "concerts", schema = "events")
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor
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

}
