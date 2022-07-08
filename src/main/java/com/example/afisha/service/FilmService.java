package com.example.afisha.service;

import com.example.afisha.dao.api.IFilmDao;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveFilmDto;
import com.example.afisha.service.api.IEventService;
import com.example.afisha.service.api.IMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FilmService implements IEventService<Film, SaveFilmDto> {
    private final IFilmDao dao;
    private final IMapper<Film, SaveFilmDto> mapper;

    public FilmService(IFilmDao dao, IMapper<Film, SaveFilmDto> mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }


    @Override
    public void save(Film film) {
            dao.save(film);
    }

    @Override
    public Film get(UUID uuid) {
        return dao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("ENTITY WASN'T FOUND "));
    }

    @Override
    public Page<Film> getAll(Pageable page) {
        return dao.findAll(Pageable.ofSize(25));
    }

    @Override
    public void update(SaveFilmDto filmDto, UUID uuid, LocalDateTime updateDate) {
        Film film = get(uuid);//GET RECORD FROM BASE

        if(!film.getUpdateDate().equals(updateDate)){
            throw new OptimisticLockException("EVENT WAS ALREADY UPDATED");//OPT LOCK
        }

        mapper.updateEntity(film, filmDto);

        dao.save(film);//SAVE-UPDATE
    }
}
