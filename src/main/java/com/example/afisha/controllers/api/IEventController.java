package com.example.afisha.controllers.api;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEventController<E,D> {
     ResponseEntity<E> get(UUID uuid);
     ResponseEntity<Page<E>> get(Integer page, Integer size );

     void save(D dto);

     void update(D dto, UUID uuid, LocalDateTime dt_update );
}
