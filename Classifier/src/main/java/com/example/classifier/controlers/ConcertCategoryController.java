package com.example.classifier.controlers;

import com.example.classifier.pagination.MyPage;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.service.api.IClassifierService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/v1/classifier/concert/category")
public class ConcertCategoryController {
    private final Predicate<SaveConcertCategoryDto> validator;
    private final IClassifierService<ConcertCategory> service;
    private final ModelMapper mapper;


    public ConcertCategoryController(Predicate<SaveConcertCategoryDto> validator,
                                     IClassifierService<ConcertCategory> service, ModelMapper mapper) {
        this.validator = validator;
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ConcertCategory> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<MyPage<ConcertCategory>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                          @RequestParam(required = false, defaultValue = "10", name = "size") Integer size){

        Page<ConcertCategory> springPage = service.getAll(PageRequest.of(page, size, Sort.by("title")));

        return new ResponseEntity<MyPage<ConcertCategory>>(mapper.map(springPage, MyPage.class ), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SaveConcertCategoryDto categoryDto){
        if(!validator.test(categoryDto)) throw new IllegalArgumentException("TITLE MUST BE FILLED");
        service.save(mapper.map(categoryDto, ConcertCategory.class));
    }
}
