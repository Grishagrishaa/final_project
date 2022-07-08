package com.example.afisha.dao.entity;

import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dao.entity.utils.LocalDateTimeDeserializer;
import com.example.afisha.dao.entity.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createDate;

    @Version
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

    private String title;
    private String description;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateEndOfSale;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    public Event(LocalDateTime createDate,
                 String title, String description,
                 LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                 EventType type, EventStatus status) {
        this.createDate = createDate;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.dateEndOfSale = dateEndOfSale;
        this.type = type;
        this.status = status;
    }

    public Event() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public Event setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
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

    @Override
    public String toString() {
        return "Event{" +
                "uuid=" + uuid +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", dateEndOfSale=" + dateEndOfSale +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
