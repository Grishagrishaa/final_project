package com.example.afisha.service;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.service.api.IDecoratorService;
import com.example.afisha.service.api.IEventService;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.afisha.dao.entity.enums.EventType.*;

@Service
public class EventServiceDecorator implements IDecoratorService {
    private final IEventService<Film> filmService;
    private final IEventService<Concert> concertService;

    public EventServiceDecorator(IEventService<Film> filmService, IEventService<Concert> concertService) {
        this.filmService = filmService;
        this.concertService = concertService;
    }

    @Override
    public BaseEvent save(BaseEvent event) {
        return getService(event.getType())
                .save(event);
    }

    @Override
    public BaseEvent get(UUID uuid, String type) {
        return getService(type)
                .get(uuid);
    }

    @Override
    public Page<? extends BaseEvent> getAll(String type, Pageable pageable) {
        return getService(type)
                .getAll(pageable);
    }

    @Override
    public BaseEvent update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate) {
        return getService(event.getType())
                .update(event, uuid, updateDate);
    }

    @Override
    public IEventService<? extends BaseEvent> getService(String type) {
        EventType eventType = valueOf(type);
        return switch (eventType) {
            case FILM -> filmService;
            case CONCERT -> concertService;
            default -> throw new TypeMismatchException("INTERNAL SERVER ERROR | CANNOT RECOGNIZE TYPE OF EVENT");
        };
    }
}
