package com.example.classifier.controlers;

import com.example.classifier.controlers.api.IClassifierController;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.dto.MyPage;
import com.example.classifier.service.dto.SaveCountryDto;
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
@RequestMapping("${app.country.url}")
public class CountryController implements IClassifierController<Country, SaveCountryDto> {
    private final IClassifierService<Country, SaveCountryDto> service;
    private final ConversionService conversionService;

    public CountryController(IClassifierService<Country, SaveCountryDto> service,
                             ConversionService conversionService) {
        this.service = service;
        this.conversionService = conversionService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Country> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MyPage<Country>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page")Integer page,
                                                  @RequestParam(required = false, defaultValue = "10", name = "size") Integer size){

        Page<Country> springPage = service.getAll(PageRequest.of(page, size, Sort.by("createDate").descending()));

        return new ResponseEntity<MyPage<Country>>(conversionService.convert(springPage, MyPage.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Country> create(@RequestBody SaveCountryDto countryDto){
        return new ResponseEntity<>(service.save(countryDto), HttpStatus.CREATED);
    }
}
