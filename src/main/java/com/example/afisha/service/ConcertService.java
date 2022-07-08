package com.example.afisha.service;

import com.example.afisha.dao.api.IConcertDao;
import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;
import com.example.afisha.service.api.IEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class ConcertService implements IEventService<Concert, SaveConcertDto> {
    private final IConcertDao dao;

    public ConcertService(IConcertDao dao) {
        this.dao = dao;
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

        if(concertDto.getTitle() != null)concert.setTitle(concertDto.getTitle());
        if(concertDto.getDescription() != null)concert.setDescription(concertDto.getDescription());
        if(concertDto.getEventDate() != null)concert.setEventDate(concertDto.getEventDate());
        if(concertDto.getDateEndOfSale() != null)concert.setDateEndOfSale(concertDto.getDateEndOfSale());//UPDATING DATA
        if(concertDto.getType() != null)concert.setType(concertDto.getType());
        if(concertDto.getStatus() != null)concert.setStatus(concertDto.getStatus());
        if(concertDto.getCategory() != null)concert.setCategory(concertDto.getCategory());

        dao.save(concert);//SAVE-UPDATE
    }
}
