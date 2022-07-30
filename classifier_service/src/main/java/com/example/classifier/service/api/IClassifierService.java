package com.example.classifier.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface IClassifierService<E> {//E -> ENTITY (COUNTRY OR CATEGORY)

    /**
     * Saving entity in db
     * @param record Entity provided for save
     */
    void save(E record);

    /**
     *
     * @param page  page parameters
     * @return Page of Entities
     */
    Page<E> getAll(Pageable page);

    /**
     *
     * @param uuid  entity id
     * @return one entity, depends on id
     */
    E get(UUID uuid);

}
