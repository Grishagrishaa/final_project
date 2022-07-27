package com.example.classifier.service;

import com.example.classifier.dao.api.ICountryDao;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.api.IClassifierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CountryService implements IClassifierService<Country> {
    private static final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final ICountryDao dao;

    public CountryService(ICountryDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void save(Country country) {
        log.info("SAVE COUNTRY " + country);
        dao.save(country);
    }

    @Override
    public Page<Country> getAll(Pageable page) {
        Page<Country> all = dao.findAll(page);
        log.info("GET PAGE OF COUNTRIES, ELEMENTS " + all.getTotalElements());
        return all;
    }

    @Override
    public Country get(UUID uuid) {
        Country country = dao.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
        log.info("GET COUNTRY " + country);
        return country;
    }
}
