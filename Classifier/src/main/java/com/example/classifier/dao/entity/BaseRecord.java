package com.example.classifier.dao.entity;

import com.example.classifier.dao.entity.utils.LocalDateTimeDeserializer;
import com.example.classifier.dao.entity.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseRecord {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID uuid;

    @JsonFormat(pattern = "yyyy-MM-dd | HH:mm:ss.SSS")
    @CreatedDate
    private LocalDateTime createDate;
    @Version
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

    public BaseRecord(UUID uuid, LocalDateTime createDate, LocalDateTime updateDate) {
        this.uuid = uuid;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public BaseRecord() {
        this.createDate = LocalDateTime.now();
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

    @Override
    public String toString() {
        return "BaseRecord{" +
                "uuid=" + uuid +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
