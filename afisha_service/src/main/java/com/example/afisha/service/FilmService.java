package com.example.afisha.service;

import com.example.afisha.dao.api.IEventFilmDao;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserHolder;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.validation.OwnershipPredicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiPredicate;

import static com.example.afisha.dao.entity.enums.EventStatus.*;


@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FilmService implements IEventService<Film> {

    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final IEventFilmDao filmDao;
    private final ModelMapper mapper;
    private final BiPredicate<String, String> ownerValidator;
    private final UserHolder userHolder;


    @Override
    @Transactional
    @CachePut(cacheNames = "film")
    public Film save(BaseEvent event) {
        Film film = (Film) event;
        log.info("SAVE FILM - {}", film);
        return filmDao.save(film);
    }

    @Override
    @Cacheable(cacheNames = "film")
    public Film get(UUID uuid) {

        return switch (userHolder.getAuthority()) {
            case ADMIN -> filmDao.findById(uuid)//ADMIN
                    .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND"));
            case USER -> filmDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, UserHolder.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND "));
            default -> filmDao.findByUuidIsAndStatusIs(uuid, PUBLISHED)
                    .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND "));
        };

    }

    @Override
    @Cacheable(cacheNames = "film")
    public Page<? extends BaseEvent> getAll(Pageable pageable) {
        return switch (userHolder.getAuthority()) {
            case ADMIN -> filmDao.findAll(pageable);
            case USER -> filmDao.findAllByStatusIsOrAuthorIs(PUBLISHED, UserHolder.getUsername(), pageable);
            default -> filmDao.findAllByStatusIs(PUBLISHED, pageable);
        };
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "film")
    public Film update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
        Film film = get(uuid);
        ownerValidator.test(film.getAuthor(), UserHolder.getUsername());

        Film filmUpdate = (Film) eventDto.getEntity();

        if(!film.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("FILM WAS ALREADY UPDATED");
        }

        mapper.map(filmUpdate, film);
        log.info("SAVE UPDATED FILM - {}", film);

        return filmDao.save(film);
    }

}
