package com.example.afisha.dao.api;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dao.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.DiscriminatorValue;
import java.util.UUID;

@Repository
public interface IEventConcertDao extends JpaRepository<Concert, UUID> {
}
