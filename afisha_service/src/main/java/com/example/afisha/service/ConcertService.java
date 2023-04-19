package com.example.afisha.service;

import com.example.afisha.dao.api.IConcertDao;
import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveEventDtoFactory;
import com.example.afisha.security.UserHolder;
import com.example.afisha.service.api.IEventService;
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

import static com.example.afisha.dao.entity.enums.EventStatus.PUBLISHED;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConcertService implements IEventService<Concert> {

    private static final Logger log = LoggerFactory.getLogger(ConcertService.class);
    private final IConcertDao concertDao;
    private final ModelMapper mapper;
    private final BiPredicate<String, String> ownerValidator;
    private final UserHolder userHolder;


    @Override
    @Transactional
    @CachePut(cacheNames = "concert")
    public Concert save(BaseEvent event) {
        Concert concert = (Concert) event;
        log.info("SAVE CONCERT - {}", concert);
        return concertDao.save(concert);
    }

    @Override
    @Cacheable(cacheNames = "concert")
    public Concert get(UUID uuid) {

        return switch (userHolder.getAuthority()) {
            case ADMIN ->//ADMIN
                    concertDao.findById(uuid)
                            .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND"));
            case USER ->//AUTHORIZED - USER
                    concertDao.findByUuidIsOrStatusIsOrAuthorIs(uuid, PUBLISHED, UserHolder.getUsername())
                            .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND "));
            default -> //NOT AUTHORIZED
                    concertDao.findByUuidIsAndStatusIs(uuid, PUBLISHED)
                            .orElseThrow(() -> new IllegalArgumentException("FILM WASN'T FOUND "));
        };

    }

    @Override
    @Cacheable(cacheNames = "concert")
    public Page<? extends BaseEvent> getAll(Pageable pageable) {

        return switch (userHolder.getAuthority()) {
            case ADMIN ->//ADMIN
                    concertDao.findAll(pageable);
            case USER ->//AUTHORIZED - USER
                    concertDao.findAllByStatusIsOrAuthorIs(PUBLISHED, UserHolder.getUsername(), pageable);
            default ->  //NOT AUTHORIZED
                    concertDao.findAllByStatusIs(PUBLISHED, pageable);
        };

    }

    @Override
    @Transactional
    @CachePut(cacheNames = "concert")
    public Concert update(SaveEventDtoFactory eventDto, UUID uuid, LocalDateTime updateDate) {
        Concert concert = get(uuid);
        ownerValidator.test(concert.getAuthor(), UserHolder.getUsername());

        Concert concertUpdate = (Concert) eventDto.getEntity();

        if(!concert.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("FILM WAS ALREADY UPDATED");
        }

        mapper.map(concertUpdate, concert);
        log.info("SAVE UPDATED CONCERT  - {}", concert);

        return concertDao.save(concert);
    }

}
