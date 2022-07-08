package com.example.afisha.service.api;

import com.example.afisha.dao.entity.Concert;
import com.example.afisha.dto.SaveConcertDto;

public interface IMapper<E, D> {//E -> ENTITY, D -> DTO
    E mapToEntity(D dto);

    void updateEntity(E record, D dto);

}
