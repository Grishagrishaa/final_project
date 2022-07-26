package com.example.afisha.service;

import com.example.afisha.dao.api.IEventConcertDao;
import com.example.afisha.dao.api.IEventFilmDao;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.api.IEvent;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserDetailsUser;
import com.example.afisha.security.UserHolder;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.validation.ConcertPredicate;
import com.example.afisha.validation.FilmPredicate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.afisha.dao.entity.enums.EventStatus.*;
import static com.example.afisha.dao.entity.enums.EventType.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class EventService implements IEventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final IEventFilmDao filmDao;
    private final IEventConcertDao concertDao;
    private final ModelMapper mapper;
    private final WebClient webClient;
    private final FilmPredicate filmValidator;
    private final ConcertPredicate concertValidator;
    private final UserHolder userHolder;


    public EventService(IEventFilmDao filmDao, IEventConcertDao concertDao,
                        ModelMapper mapper, WebClient webClient,
                        FilmPredicate filmValidator, ConcertPredicate concertValidator,
                        UserHolder userHolder) {
        this.filmDao = filmDao;
        this.concertDao = concertDao;
        this.mapper = mapper;
        this.webClient = webClient;
        this.filmValidator = filmValidator;
        this.concertValidator = concertValidator;
        this.userHolder = userHolder;
    }

    @Override
    public void save(IEvent event) {
        switch (event.getType()){
            case FILM:
                Film film = (Film) event;
                filmValidator.test(film);
                log.info("SAVE FILM " + film);
                filmDao.save(film);
                break;
            case CONCERT:
                Concert concert = (Concert) event;
                concertValidator.test(concert);
                log.info("SAVE CONCERT " + concert);
                concertDao.save(concert);

                break;
        }
    }

    @Override
    public IEvent get(UUID uuid, EventType type) {
        switch (type){
            case CONCERT://ADMIN
                if(userHolder.isAdmin()) return concertDao.findById(uuid)
                        .orElseThrow( () -> new IllegalArgumentException("CONCERT WASN'T FOUND") );

                if (userHolder.isAuthenticated()) {//AUTHENTICATED
                    return concertDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, userHolder.getUsername())
                            .orElseThrow( () -> new IllegalArgumentException("CONCERT WASN'T FOUND") );
                } else {//NOT AUTHENTICATED
                    return concertDao.findByStatusIsAndUuid(PUBLISHED, uuid)
                            .orElseThrow( () -> new IllegalArgumentException("CONCERT WASN'T FOUND ") );
                }

            case FILM://ADMIN
                if(userHolder.isAdmin()) return filmDao.findById(uuid)
                        .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND") );

                if (userHolder.isAuthenticated()) {//AUTHENTICATED
                    return filmDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, userHolder.getUsername())
                            .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );

                } else {//NOT AUTHENTICATED
                    return filmDao.findByStatusIsAndUuid(PUBLISHED, uuid)
                            .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );
                }
        }
        throw new EntityNotFoundException("NOT FOUND");//NOT REAL CASE, FOR BEAUTY CODE ONLY
    }

    @Override
    public Page<? extends BaseEvent> getAll(EventType type, Pageable pageable) {
        switch(type){
            case CONCERT://ADMIN
                if(userHolder.isAdmin()) return concertDao.findAll(pageable);

                if(userHolder.isAuthenticated()){//AUTHENTICATED
                    return concertDao.findAllByStatusIsOrAuthorIs(PUBLISHED,
                            userHolder.getUsername(),
                            pageable);
                }else {//NOT AUTHENTICATED
                    return concertDao.findAllByStatusIs(PUBLISHED, pageable);
                }
            case FILM://ADMIN
                if(userHolder.isAdmin()) return filmDao.findAll(pageable);

                if(userHolder.isAuthenticated()){//AUTHENTICATED
                    return filmDao.findAllByStatusIsOrAuthorIs(PUBLISHED,
                            userHolder.getUsername(),
                            pageable);
                }else {//NOT AUTHENTICATED
                    return filmDao.findAllByStatusIs(PUBLISHED, pageable);
                }
        }
        throw new EntityNotFoundException("NOT FOUND");//NOT REAL CASE, FOR BEAUTY CODE ONLY
    }

    @Override
    public void update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
            switch (eventDto.getType()){
                case CONCERT:
                    Concert concert = (Concert) get(uuid, CONCERT);
                    Concert concertUpdate = (Concert) eventDto.getEntity();

                    if(!concert.getUpdateDate().isEqual(updateDate)){
                        throw new OptimisticLockException("CONCERT WAS ALREADY UPDATED");
                    }

                    concertValidator.testUpdate(concertUpdate);

                    mapper.map(concertUpdate, concert);
                    log.info("SAVE UPDATED CONCERT " + concert);

                    concertDao.save(concert);
                case FILM:
                    Film film = (Film) get(uuid, FILM);
                    Film filmUpdate = (Film) eventDto.getEntity();

                    if(!film.getUpdateDate().isEqual(updateDate)){
                        throw new OptimisticLockException("FILM WAS ALREADY UPDATED");
                    }

                    filmValidator.testUpdate(filmUpdate);

                    mapper.map(filmUpdate, film);
                    log.info("SAVE UPDATED FILM " + film);

                    filmDao.save(film);
            }
    }

    public UserDetails loadUser(String headerValue) throws UsernameNotFoundException {
        UserDetailsUser user = null;
        try {
            user = webClient
                    .get()
                    .uri("http://localhost:8082/api/v1/users/me")
                    .header(AUTHORIZATION, headerValue)
                    .retrieve().bodyToMono(UserDetailsUser.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return user;
    }
}
