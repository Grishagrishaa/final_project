package com.example.classifier.controlers;

import com.example.classifier.pagination.MyPage;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.dto.SaveCountryDto;
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
@RequestMapping("/api/v1/classifier/country")
public class CountryController {
    private final IClassifierService<Country> service;
    private final Predicate<SaveCountryDto> validator;
    private final ModelMapper mapper;

    public CountryController(IClassifierService<Country> service, Predicate<SaveCountryDto> validator,
                             ModelMapper mapper) {
        this.service = service;
        this.validator = validator;
        this.mapper = mapper;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Country> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MyPage<Country>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page")Integer page,
                                                  @RequestParam(required = false, defaultValue = "10", name = "size") Integer size){

        Page<Country> springPage = service.getAll(PageRequest.of(page, size, Sort.by("title")));

        return new ResponseEntity<MyPage<Country>>(mapper.map(springPage, MyPage.class), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SaveCountryDto countryDto){
        if(!validator.test(countryDto)) throw new IllegalArgumentException("TITLE AND DESCRIPTION MUST BE FILLED");

        service.save(mapper.map(countryDto, Country.class));
    }
}
