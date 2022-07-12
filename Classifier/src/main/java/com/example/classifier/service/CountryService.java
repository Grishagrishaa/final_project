package com.example.classifier.service;

import com.example.classifier.dao.api.ICountryDao;
import com.example.classifier.dao.entity.Country;
import com.example.classifier.service.api.IClassifierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CountryService implements IClassifierService<Country> {
    private final ICountryDao dao;

    public CountryService(ICountryDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(Country country) {
        country.setCreateDate(LocalDateTime.now());
        dao.save(country);
    }

    @Override
    public Page<Country> getAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Country get(UUID uuid) {
        return dao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
    }
}
