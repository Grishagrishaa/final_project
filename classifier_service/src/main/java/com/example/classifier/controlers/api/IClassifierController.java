package com.example.classifier.controlers.api;

import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.dto.MyPage;
import com.example.classifier.service.dto.SaveCountryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface IClassifierController <E, D> {//E -> Entity, D-> DTO
    @GetMapping("/{uuid}")
    ResponseEntity<E> get(@PathVariable UUID uuid);

    @GetMapping
    ResponseEntity<MyPage<E>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page")Integer page,
                                           @RequestParam(required = false, defaultValue = "10", name = "size") Integer size);

    @PostMapping
    ResponseEntity<E> create(@RequestBody D countryDto);
}
