package com.example.afisha.service;

import com.example.afisha.dao.api.IConcertDao;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.service.api.IMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConcertService implements IEventService<Concert, SaveConcertDto> {
    private final IConcertDao dao;
    private final IMapper<Concert, SaveConcertDto> mapper;

    public ConcertService(IConcertDao dao, IMapper<Concert, SaveConcertDto> mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }


    @Override
    public void save(Concert event) {
        dao.save(event);
    }

    @Override
    public Concert get(UUID uuid) {
        return dao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("ENTITY WASN'T FOUND "));
    }

    @Override
    public Page<Concert> getAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public void update(SaveConcertDto concertDto, UUID uuid, LocalDateTime updateDate) {
        Concert concert = get(uuid);//GET RECORD FROM BASE

        if(!concert.getUpdateDate().equals(updateDate)){
            throw new OptimisticLockException("EVENT WAS ALREADY UPDATED");//OPT LOCK
        }
        mapper.updateEntity(concert, concertDto);

        dao.save(concert);//SAVE-UPDATE
    }
}
