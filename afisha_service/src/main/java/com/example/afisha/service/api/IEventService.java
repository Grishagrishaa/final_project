package com.example.afisha.service.api;


import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dto.SaveEventDtoFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEventService<E> {//E - Event entity (Concert, Film ... )

    /**
     * Save event in db
     * @param event event provided to save in db
     */
    void save(BaseEvent event);//CREATE

    /**
     * @param uuid event id
     * @return event, depends on uuid
     */
    E get(UUID uuid);//READ

    /**
     *
     * @param pageable page parameters
     * @return Page of events
     */
    Page<? extends BaseEvent> getAll(Pageable pageable);//READ

    /**
     *
     * @param event provided event with fields needed to update
     * @param uuid event id
     * @param updateDate date, when event was updated last time
     */
    void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate);//UPDATE
}
