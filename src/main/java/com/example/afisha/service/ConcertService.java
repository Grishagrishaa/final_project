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
        Concert concert = null;
        if(dao.findById(uuid).isPresent()){
            concert = dao.findById(uuid).get();
        }else{
            throw new EntityNotFoundException("INVALID UUID, NOT FOUND");
        }
        return concert;
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

        concert.setTitle(concertDto.getTitle());
        concert.setDescription(concertDto.getDescription());
        concert.setEventDate(concertDto.getEventDate());
        concert.setDateEndOfSale(concertDto.getDateEndOfSale());//UPDATING DATA
        concert.setType(concertDto.getType());
        concert.setStatus(concertDto.getStatus());
        concert.setCategory(concertDto.getCategory());

        dao.save(concert);//SAVE-UPDATE
    }
}
