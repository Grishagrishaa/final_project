package com.example.afisha.service.api;


import com.example.afisha.dao.entity.Film;
import com.example.afisha.dto.SaveFilmDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEventService<E, D> {
    void save(E event);//CREATE

    E get(UUID uuid);//READ
    Page<E> getAll(Pageable page);//READ

    void update(D event, UUID uuid, LocalDateTime updateDate);//UPDATE
}
