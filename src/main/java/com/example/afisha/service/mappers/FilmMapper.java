package com.example.afisha.service.mappers;


import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveFilmDto;
import com.example.afisha.service.api.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component    //подсмотрел у charismaticGun
public class FilmMapper implements IMapper<Film, SaveFilmDto> {
    private final ModelMapper mapper;


    public FilmMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Film mapToEntity(SaveFilmDto dto){
        Film film = mapper.map(dto, Film.class);
        film.setCreateDate(LocalDateTime.now());
        return film;
    }

    public void updateEntity(Film film, SaveFilmDto filmDto){
        if(filmDto.getTitle() != null)film.setTitle(filmDto.getTitle());
        if(filmDto.getDescription() != null)film.setDescription(filmDto.getDescription());
        if(filmDto.getEventDate() != null)film.setEventDate(filmDto.getEventDate());
        if(filmDto.getDateEndOfSale() != null)film.setDateEndOfSale(filmDto.getDateEndOfSale());//UPDATING DATA
        if(filmDto.getType() != null)film.setType(filmDto.getType());
        if(filmDto.getStatus() != null)film.setStatus(filmDto.getStatus());
        if(filmDto.getCountry() != null)film.setCountry(filmDto.getCountry());
        if(filmDto.getReleaseYear() != null)film.setReleaseYear(filmDto.getReleaseYear());
        if(filmDto.getReleaseDate() != null)film.setReleaseDate(filmDto.getReleaseDate());
        if(filmDto.getDuration() != null)film.setDuration(filmDto.getDuration());
    }



}
