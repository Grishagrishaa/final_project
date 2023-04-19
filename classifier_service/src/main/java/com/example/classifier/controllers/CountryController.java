package com.example.classifier.controllers;

import com.example.classifier.controllers.api.IClassifierController;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.api.IClassifierService;
import com.example.classifier.service.dto.MyPage;
import com.example.classifier.service.dto.SaveCountryDto;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;


import java.util.UUID;

@RestController
@RequestMapping("${app.country.url}")
@RequiredArgsConstructor
public class CountryController implements IClassifierController<Country, SaveCountryDto> {
    private final IClassifierService<Country, SaveCountryDto> service;
    private final ConversionService conversionService;


    @GetMapping("/{uuid}")
    public ResponseEntity<Country> get(UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MyPage<Country>> getAll(Pageable pageable){

        Page<Country> springPage = service.getAll(pageable);

        return ResponseEntity.ok().body(conversionService.convert(springPage, MyPage.class));
    }

    @PostMapping
    public ResponseEntity<Country> create(SaveCountryDto countryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(countryDto));
    }
}
