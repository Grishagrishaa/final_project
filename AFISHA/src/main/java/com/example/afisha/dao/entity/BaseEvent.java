package com.example.afisha.dao.entity;

import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventStatus;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dao.entity.utils.LocalDateTimeDeserializer;
import com.example.afisha.dao.entity.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEvent implements IEvent {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd | HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @Version
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy.MM.dd | HH:mm")
    private LocalDateTime eventDate;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDateTime dateEndOfSale;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;
    @JsonIgnore
    private String author;

    public BaseEvent(String title, String description,
                     LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                     EventType type, EventStatus status,
                     String author) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.dateEndOfSale = dateEndOfSale;
        this.type = type;
        this.status = status;
        this.author = author;
    }

    public BaseEvent() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
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

    @Override
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
