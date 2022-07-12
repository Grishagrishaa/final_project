package com.example.classifier.dao.entity;

import com.example.classifier.dao.entity.utils.LocalDateTimeDeserializer;
import com.example.classifier.dao.entity.utils.LocalDateTimeSerializer;
import com.example.classifier.dto.SaveCountryDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "country", schema = "directory")
public class Country {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd | HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @Version
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

    private String title;
    private String description;

    public Country(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate, String title, String description) {
        this.uuid = uuid;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.title = title;
        this.description = description;
    }

    public Country() {
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
}
