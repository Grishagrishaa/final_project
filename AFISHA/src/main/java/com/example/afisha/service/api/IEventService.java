package com.example.afisha.service.api;


import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEventService {//docs
    void save(IEvent event);//CREATE

    IEvent get(UUID uuid, EventType type);//READ
    Page<? extends BaseEvent> getAll(EventType type, Pageable pageable);//READ

    void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE

}
