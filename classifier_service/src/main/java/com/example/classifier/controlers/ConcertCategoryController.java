package com.example.classifier.controlers;

import com.example.classifier.controlers.api.IClassifierController;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.service.dto.MyPage;
import com.example.classifier.service.dto.SaveConcertCategoryDto;
import com.example.classifier.service.api.IClassifierService;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${app.concertCategory.url}")
public class ConcertCategoryController implements IClassifierController<ConcertCategory, SaveConcertCategoryDto> {
    private final IClassifierService<ConcertCategory, SaveConcertCategoryDto> service;
    private final ConversionService conversionService;


    public ConcertCategoryController(IClassifierService<ConcertCategory, SaveConcertCategoryDto> service, ConversionService conversionService) {
        this.service = service;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ConcertCategory> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MyPage<ConcertCategory>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                          @RequestParam(required = false, defaultValue = "10", name = "size") Integer size){

        Page<ConcertCategory> springPage = service.getAll(PageRequest.of(page, size, Sort.by("createDate").descending()));

        return new ResponseEntity<MyPage<ConcertCategory>>(conversionService.convert(springPage, MyPage.class ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConcertCategory> create(@RequestBody SaveConcertCategoryDto categoryDto){
        return new ResponseEntity<>(service.save(categoryDto), HttpStatus.CREATED);
    }
}
