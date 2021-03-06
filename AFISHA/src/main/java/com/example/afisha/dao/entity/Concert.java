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

    public Concert(LocalDateTime createDate,
                   String title, String description,
                   LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                   EventType type, EventStatus status,
                   UUID category) {
        super(createDate, title, description, eventDate, dateEndOfSale, type, status);
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
        return "EventConcert{" +
                " uuid=" + getUuid() +
                ", createDate=" + getCreateDate() +
                ", updateDate=" + getUpdateDate() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", eventDate=" + getEventDate() +
                ", dateEndOfSale=" + getDateEndOfSale() +
                ", type=" + getType() +
                ", status=" + getStatus() +
                ",  category=" + category +
                '}';
    }
}
