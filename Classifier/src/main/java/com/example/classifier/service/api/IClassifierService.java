package com.example.classifier.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface IClassifierService<E> {//E -> ENTITY

    void save(E record);

    Page<E> getAll(Pageable page);

    E get(UUID uuid);

}
