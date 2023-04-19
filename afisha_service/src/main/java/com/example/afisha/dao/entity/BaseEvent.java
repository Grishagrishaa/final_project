package com.example.afisha.dao.entity;

import com.example.afisha.dao.entity.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data @NoArgsConstructor
public abstract class BaseEvent{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @CreatedDate
    private LocalDateTime createDate;

    @Version
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateDate;

    private String title;
    private String description;
    private LocalDateTime eventDate;
    private LocalDateTime dateEndOfSale;
    private String type;
    private String status;
    @JsonIgnore
    private String author;

    public BaseEvent(String title, String description,
                     LocalDateTime eventDate, LocalDateTime dateEndOfSale,
                     String type, String status,
                     String author) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.dateEndOfSale = dateEndOfSale;
        this.type = type;
        this.status = status;
        this.author = author;
    }

}
