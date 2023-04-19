package com.example.afisha.dao.api;

import com.example.afisha.dao.entity.Film;
import com.example.afisha.dao.entity.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface IEventFilmDao extends JpaRepository<Film, UUID> {

    /**
     * AUTHORIZED
     * @param status FILM Status
     * @param author author of FILM
     * @param pageable page parameters
     * @return Page of FILMS
     */
    Page<Film> findAllByStatusIsOrAuthorIs(EventStatus status, String author, Pageable pageable);


    /**
     * NOT AUTHORIZED
     * @param status Film Status
     * @param pageable page parameters
     * @return Page of Films
     */
    Page<Film> findAllByStatusIs(EventStatus status, Pageable pageable);


    /**
     * AUTHORIZED
     * @param uuid Film ID
     * @param status Film Status
     * @param author author of Film
     * @return one Film
     */
    @Query("SELECT film FROM Film film WHERE film.uuid = ?1 and (film.status = ?2 or film.author = ?3)")
    Optional<Film> findByUuidIsOrStatusIsOrAuthorIs(UUID uuid, EventStatus status, String author);


    /**
     * NOT AUTHORIZED
     * @param status Film Status
     * @param uuid Film ID
     * @return one Film
     */
    Optional<Film> findByUuidIsAndStatusIs(UUID uuid, EventStatus status);

}

