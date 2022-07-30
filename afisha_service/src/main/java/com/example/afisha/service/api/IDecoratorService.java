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

/**
 * Decorator service for Events services.
 * Every method delegate realization to appropriate service, depends on Type of Event
 */
public interface IDecoratorService {
    void save(BaseEvent event);//CREATE

    /**
     *
     * @param uuid event id
     * @param type type of event
     * @return event, depends on type and uuid
     */
    BaseEvent get(UUID uuid, EventType type);//READ

    /**
     *
     * @param type type of event to show
     * @param pageable - page parameters
     * @return Page of events
     */
    Page<? extends BaseEvent> getAll(EventType type, Pageable pageable);//READ

    /**
     *
     * @param event event with fields needed update
     * @param uuid event id
     * @param updateDate date, when event was updated last time
     */
    void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE

    /**
     *
     * @param type type of event provided to Service
     * @return appropriate service for delegating realization of method in Service
     */
    IEventService<? extends BaseEvent> getService(EventType type);

    /**
     *
     * @param headerValue header, provided by user to auth
     * @return user from DB if exists( was signed up earlier )
     * @throws UsernameNotFoundException if user not found in db ( wasn't signed up earlier )
     */
    UserDetails loadUser(String headerValue) throws UsernameNotFoundException;
}
