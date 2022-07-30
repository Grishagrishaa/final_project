package com.example.afisha.service;

import com.example.afisha.dao.api.IConcertDao;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserHolder;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.validation.ConcertValidationPredicate;
import com.example.afisha.validation.OwnershipPredicate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.afisha.dao.entity.enums.EventStatus.PUBLISHED;

@Component
@Transactional(readOnly = true)
public class ConcertService implements IEventService<Concert> {
    private static final Logger log = LoggerFactory.getLogger(ConcertService.class);
    private final IConcertDao concertDao;
    private final ModelMapper mapper;
    private final ConcertValidationPredicate concertValidator;
    private final OwnershipPredicate ownerValidator;
    private final UserHolder userHolder;

    public ConcertService(IConcertDao concertDao, ModelMapper mapper, ConcertValidationPredicate concertValidator, OwnershipPredicate ownerValidator, UserHolder userHolder) {
        this.concertDao = concertDao;
        this.mapper = mapper;
        this.concertValidator = concertValidator;
        this.ownerValidator = ownerValidator;
        this.userHolder = userHolder;
    }

    @Override
    @Transactional
    public void save(BaseEvent event) {
        Concert concert = (Concert) event;
        concertValidator.test(concert);
        log.info("SAVE FILM " + concert);
        concertDao.save(concert);
    }

    @Override
    public Concert get(UUID uuid) {

        switch (userHolder.getAuthority()){
            case ADMIN://ADMIN
                return concertDao.findById(uuid)
                              .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND") );
            case USER://AUTHORIZED - USER
                return concertDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, UserHolder.getUsername())
                               .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );
            default: //NOT AUTHORIZED
                return concertDao.findByUuidIsAndStatusIs(uuid, PUBLISHED)
                              .orElseThrow( () -> new IllegalArgumentException("FILM WASN'T FOUND ") );
        }

    }

    @Override
    public Page<? extends BaseEvent> getAll(Pageable pageable) {

        switch (userHolder.getAuthority()){
            case ADMIN://ADMIN
                return concertDao.findAll(pageable);
            case USER://AUTHORIZED - USER
                return concertDao.findAllByStatusIsOrAuthorIs(PUBLISHED, UserHolder.getUsername(), pageable);
            default:  //NOT AUTHORIZED
                return concertDao.findAllByStatusIs(PUBLISHED, pageable);
        }

    }

    @Override
    @Transactional
    public void update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
        Concert concert = get(uuid);
        ownerValidator.test(concert.getAuthor(), UserHolder.getUsername());

        Concert concertUpdate = (Concert) eventDto.getEntity();

        if(!concert.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("FILM WAS ALREADY UPDATED");
        }

        concertValidator.testUpdate(concertUpdate);

        mapper.map(concertUpdate, concert);
        log.info("SAVE UPDATED CONCERT " + concert);

        concertDao.save(concert);
    }
}
