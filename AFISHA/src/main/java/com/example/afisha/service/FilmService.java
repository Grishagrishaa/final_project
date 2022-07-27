package com.example.afisha.service;

import com.example.afisha.dao.api.IEventFilmDao;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserHolder;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.validation.FilmValidationPredicate;
import com.example.afisha.validation.OwnershipPredicate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.afisha.dao.entity.enums.EventStatus.*;


@Component
@Transactional(readOnly = true)
public class FilmService implements IEventService<Film> {
    private static final Logger log = LoggerFactory.getLogger(FilmService.class);
    private final IEventFilmDao filmDao;
    private final ModelMapper mapper;
    private final FilmValidationPredicate filmValidator;
    private final OwnershipPredicate ownerValidator;
    private final UserHolder userHolder;

    public FilmService(IEventFilmDao filmDao, ModelMapper mapper, FilmValidationPredicate filmValidator, OwnershipPredicate ownerValidator, UserHolder userHolder) {
        this.filmDao = filmDao;
        this.mapper = mapper;
        this.filmValidator = filmValidator;
        this.ownerValidator = ownerValidator;
        this.userHolder = userHolder;
    }


    @Override
    @Transactional
    public void save(BaseEvent event) {
        Film film = (Film) event;
        filmValidator.test(film);
        log.info("SAVE FILM " + film);
        filmDao.save(film);
    }

    @Override
    public Film get(UUID uuid) {

        switch (userHolder.getAuthority()){
            case ADMIN:
                return filmDao.findById(uuid)//ADMIN
                              .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND") );
            case USER:
                return filmDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, userHolder.getUsername())
                               .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );
            default:
                return filmDao.findByUuidIsAndStatusIs(PUBLISHED, uuid)
                              .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );
        }

    }

    @Override
    public Page<? extends BaseEvent> getAll(Pageable pageable) {

        switch (userHolder.getAuthority()){
            case ADMIN:
                return filmDao.findAll(pageable);
            case USER:
                return filmDao.findAllByStatusIsOrAuthorIs(PUBLISHED, userHolder.getUsername(), pageable);
            default:
                return filmDao.findAllByStatusIs(PUBLISHED, pageable);
        }

    }

    @Override
    @Transactional
    public void update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
        Film film = get(uuid);
        ownerValidator.test(film.getAuthor(), userHolder.getUsername());

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
