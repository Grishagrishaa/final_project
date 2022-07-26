package com.example.classifier.dao.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "country", schema = "directory")
@EntityListeners(AuditingEntityListener.class)
public class Country extends BaseRecord {

    private String title;
    private String description;

    public Country(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate, String title, String description) {
        super(uuid, createDate, updateDate);
        this.title = title;
        this.description = description;
    }

    public Country() {
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

    @Override
    public String toString() {
        return "Country{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

