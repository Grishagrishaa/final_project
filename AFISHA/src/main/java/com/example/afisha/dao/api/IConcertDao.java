package com.example.afisha.dao.api;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.enums.EventStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IConcertDao extends JpaRepository<Concert, UUID> {
    //AUTHORIZED
    Page<Concert> findAllByStatusIsOrAuthorIs(EventStatus status, String author, Pageable pageable);
    //NOT AUTHORIZED
    Page<Concert> findAllByStatusIs(EventStatus status, Pageable pageable);

    //AUTHORIZED
    @Query("SELECT concert FROM Concert concert WHERE concert.uuid = ?1 and (concert.status = ?2 or concert.author = ?3)")
    Optional<Concert> findByUuidIsOrStatusIsOrAuthorIs(UUID uuid, EventStatus status, String author);
    //NOT AUTHORIZED
    Optional<Concert> findByUuidIsAndStatusIs(EventStatus status, UUID uuid);


}