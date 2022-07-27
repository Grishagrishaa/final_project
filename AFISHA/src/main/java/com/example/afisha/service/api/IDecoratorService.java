package com.example.afisha.service.api;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IDecoratorService {
    void save(BaseEvent event);//CREATE

    BaseEvent get(UUID uuid, EventType type);//READ
    Page<? extends BaseEvent> getAll(EventType type, Pageable pageable);//READ

    void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE

    IEventService<? extends BaseEvent> getService(EventType type);

    UserDetails loadUser(String headerValue) throws UsernameNotFoundException;
}
