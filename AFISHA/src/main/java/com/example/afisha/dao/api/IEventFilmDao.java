package com.example.afisha.dao.api;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface IEventFilmDao extends JpaRepository<Film, UUID> {
    //AUTHORIZED
    Page<Film> findAllByStatusIsOrAuthorIs(EventStatus status, String author, Pageable pageable);
    //NOT AUTHORIZED
    Page<Film> findAllByStatusIs(EventStatus status, Pageable pageable);

    //AUTHORIZED
    @Query("SELECT film FROM Film film WHERE film.uuid = ?1 and (film.status = ?2 or film.author = ?3)")
    Optional<Film> findByUuidIsOrStatusIsOrAuthorIs(UUID uuid, EventStatus status, String author);
    //NOT AUTHORIZED
    Optional<Film> findByUuidIsAndStatusIs(EventStatus status, UUID uuid);
}
