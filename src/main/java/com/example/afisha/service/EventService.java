package com.example.afisha.service;

import com.example.afisha.dao.api.IEventConcertDao;
import com.example.afisha.dao.api.IEventFilmDao;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Event;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.service.api.IEventService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventService implements IEventService {
    private final IEventFilmDao filmDao;
    private final IEventConcertDao concertDao;
    private final ModelMapper mapper;

    public EventService(IEventFilmDao filmDao, IEventConcertDao concertDao,
                        ModelMapper mapper) {
        this.filmDao = filmDao;
        this.concertDao = concertDao;
        this.mapper = mapper;
    }

    @Override
    public void save(IEvent event) {
        switch (event.getType()){
            case FILM:
                filmDao.save((Film) event);
                break;
            case CONCERT:
                concertDao.save((Concert) event);
                break;
        }
    }

    @Override
    public IEvent get(UUID uuid, EventType type) {
        if(EventType.CONCERT.equals(type)){
            return concertDao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("ENTITY WASN'T FOUND "));
        }else {
            return filmDao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("ENTITY WASN'T FOUND "));
        }
    }

    @Override
    public Page<? extends Event> getAll(EventType type, Pageable pageable) {
        if(EventType.CONCERT.equals(type)){
            return concertDao.findAll(pageable);
        }else {
            return filmDao.findAll(pageable);
        }
    }

    @Override
    public void update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
        if (EventType.CONCERT.equals(eventDto.getType())){
            Concert concert = (Concert) get(uuid, eventDto.getType());

            if(!concert.getUpdateDate().isEqual(updateDate)){
                throw new OptimisticLockException("CONCERT WAS ALREADY UPDATED");
            }

            mapper.map(eventDto, concert);

            concertDao.save(concert);
        }else{
            Film film = (Film) get(uuid, eventDto.getType());

            if(!film.getUpdateDate().isEqual(updateDate)){
                throw new OptimisticLockException("FILM WAS ALREADY UPDATED");
            }

            mapper.map(eventDto, film);

            filmDao.save(film);
        }
    }

}
