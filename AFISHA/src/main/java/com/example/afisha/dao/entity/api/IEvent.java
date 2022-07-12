package com.example.afisha.dao.entity.api;

import com.example.afisha.dao.entity.Event;
import com.example.afisha.dao.entity.enums.EventType;

import java.time.LocalDateTime;

public interface IEvent {
    EventType getType();

    LocalDateTime getUpdateDate();
}
