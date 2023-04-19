package com.example.classifier.controllers.api;

import com.example.classifier.service.dto.MyPage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface IClassifierController <E, D> {//E -> Entity, D-> DTO
    @GetMapping("/{uuid}")
    ResponseEntity<E> get(@PathVariable UUID uuid);

    @GetMapping
    ResponseEntity<MyPage<E>> getAll(@PageableDefault Pageable pageable);

    @PostMapping
    ResponseEntity<E> create(@RequestBody D countryDto);
}
