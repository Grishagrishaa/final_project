package com.example.classifier.service;

import com.example.classifier.dao.api.ICountryDao;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.api.IClassifierService;
import com.example.classifier.service.dto.SaveCountryDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.UUID;

@Service
@Validated
@Transactional(readOnly = true)
public class CountryService implements IClassifierService<Country, SaveCountryDto> {
    private static final Logger log = LoggerFactory.getLogger(CountryService.class);
    private final ConversionService conversionService;
    private final ICountryDao dao;

    public CountryService(ConversionService conversionService, ICountryDao dao) {
        this.conversionService = conversionService;
        this.dao = dao;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "country")
    public Country save(@Valid SaveCountryDto countryDto) {
        Country country = conversionService.convert(countryDto, Country.class);
        log.info("SAVE COUNTRY - {}", country);
        return dao.save(country);
    }

    @Override
    @Cacheable(cacheNames = "country")
    public Page<Country> getAll(Pageable page) {
        Page<Country> all = dao.findAll(page);
        log.info("GET PAGE OF COUNTRIES, ELEMENTS - {}", all.getTotalElements());
        return all;
    }

    @Override
    @Cacheable(cacheNames = "country")
    public Country get(UUID uuid) {
        Country country = dao.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
        log.info("GET COUNTRY - {}", country);
        return country;
    }
}
