package com.example.classifier.controllers;

import com.example.classifier.controllers.api.IClassifierController;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.service.api.IClassifierService;
import com.example.classifier.service.dto.MyPage;
import com.example.classifier.service.dto.SaveConcertCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("${app.concertCategory.url}")
@RequiredArgsConstructor
public class ConcertCategoryController implements IClassifierController<ConcertCategory, SaveConcertCategoryDto> {
    private final IClassifierService<ConcertCategory, SaveConcertCategoryDto> service;
    private final ConversionService conversionService;


    @GetMapping("/{uuid}")
    public ResponseEntity<ConcertCategory> get(UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MyPage<ConcertCategory>> getAll(Pageable pageable){

        Page<ConcertCategory> springPage = service.getAll(pageable);

        return new ResponseEntity<MyPage<ConcertCategory>>(conversionService.convert(springPage, MyPage.class ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConcertCategory> create(SaveConcertCategoryDto categoryDto){
        return new ResponseEntity<>(service.save(categoryDto), HttpStatus.CREATED);
    }
}
