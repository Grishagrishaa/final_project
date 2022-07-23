package com.example.classifier.dao.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "concertCategory", schema = "directory")
public class ConcertCategory extends BaseRecord {

    private String title;

    public ConcertCategory(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate, String title) {
        super(uuid, LocalDateTime.now(), updateDate);
        this.title = title;
    }

    public ConcertCategory(String title) {
        this.title = title;
    }

    public ConcertCategory() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
