package com.example.afisha.service.mappers;


import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;
import com.example.afisha.service.api.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component    //подсмотрел у charismaticGun
public class ConcertMapper implements IMapper<Concert, SaveConcertDto> {
    private final ModelMapper mapper;


    public ConcertMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Concert mapToEntity(SaveConcertDto dto){
        Concert concert = mapper.map(dto, Concert.class);
        concert.setCreateDate(LocalDateTime.now());
        return concert;
    }

    public void updateEntity(Concert concert, SaveConcertDto concertDto){
        if(concertDto.getTitle() != null)concert.setTitle(concertDto.getTitle());
        if(concertDto.getDescription() != null)concert.setDescription(concertDto.getDescription());
        if(concertDto.getEventDate() != null)concert.setEventDate(concertDto.getEventDate());
        if(concertDto.getDateEndOfSale() != null)concert.setDateEndOfSale(concertDto.getDateEndOfSale());//UPDATING DATA
        if(concertDto.getType() != null)concert.setType(concertDto.getType());
        if(concertDto.getStatus() != null)concert.setStatus(concertDto.getStatus());
        if(concertDto.getCategory() != null)concert.setCategory(concertDto.getCategory());
    }



}
