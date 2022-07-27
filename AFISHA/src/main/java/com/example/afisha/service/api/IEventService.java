package com.example.afisha.service.api;


import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEventService<E> {//docs
    void save(BaseEvent event);//CREATE

    E get(UUID uuid);//READ
    Page<? extends BaseEvent> getAll(Pageable pageable);//READ

    void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE
}
