package com.example.afisha.service.api;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Decorator service for Events services.
 * Every method delegate realization to appropriate service, depends on Type of Event
 */
public interface IDecoratorService {
    BaseEvent save(BaseEvent event);//CREATE

    /**
     *
     * @param uuid event id
     * @param type type of event
     * @return event, depends on type and uuid
     */
    BaseEvent get(UUID uuid, String type);//READ

    /**
     *
     * @param type type of event to show
     * @param pageable - page parameters
     * @return Page of events
     */
    Page<? extends BaseEvent> getAll(String type, Pageable pageable);//READ

    /**
     * @param event      event with fields needed update
     * @param uuid       event id
     * @param updateDate date, when event was updated last time
     * @return
     */
    BaseEvent update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE

    /**
     *
     * @param type type of event provided to Service
     * @return appropriate service for delegating realization of method in Service
     */
    IEventService<? extends BaseEvent> getService(String type);
}
