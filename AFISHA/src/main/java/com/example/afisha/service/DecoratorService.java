package com.example.afisha.service;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventType;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserDetailsUser;
import com.example.afisha.service.api.IDecoratorService;
import com.example.afisha.service.api.IEventService;
import org.hibernate.TypeMismatchException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class DecoratorService implements IDecoratorService {
    private final WebClient webClient;

    private final IEventService<Film> filmService;
    private final IEventService<Concert> concertService;

    public DecoratorService(WebClient webClient, IEventService<Film> filmService, IEventService<Concert> concertService) {
        this.webClient = webClient;
        this.filmService = filmService;
        this.concertService = concertService;
    }

    @Override
    public void save(BaseEvent event) {
        getService(event.getType())
                .save(event);
    }

    @Override
    public BaseEvent get(UUID uuid, EventType type) {
        return getService(type)
                .get(uuid);
    }

    @Override
    public Page<? extends BaseEvent> getAll(EventType type, Pageable pageable) {
        return getService(type)
                .getAll(pageable);
    }

    @Override
    public void update(SaveEventDtoFactory event, UUID uuid, LocalDateTime updateDate) {
        getService(event.getType())
                .update(event, uuid, updateDate);
    }

    @Override
    public IEventService<? extends BaseEvent> getService(EventType type) {
        switch (type){
            case FILM:
                return filmService;
            case CONCERT:
                return concertService;
            default:
                throw new TypeMismatchException("INTERNAL SERVER ERROR | CANNOT RECOGNIZE TYPE OF EVENT");
        }
    }

    @Override
    public UserDetails loadUser(String headerValue) throws UsernameNotFoundException {
        UserDetailsUser user = null;
        try {
            user = webClient
                    .get()
                    .uri("http://localhost:8082/api/v1/users/me")
                    .header(AUTHORIZATION, headerValue)
                    .retrieve().bodyToMono(UserDetailsUser.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            throw new UsernameNotFoundException("USER NOT FOUND | INVALID TOKEN");
        }
        return user;
    }
}
