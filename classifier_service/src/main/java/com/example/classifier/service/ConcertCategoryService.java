package com.example.classifier.service;

import com.example.classifier.dao.api.IConcertCategoryDao;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.service.api.IClassifierService;
import com.example.classifier.service.dto.SaveConcertCategoryDto;
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
public class ConcertCategoryService implements IClassifierService<ConcertCategory, SaveConcertCategoryDto> {
    private static final Logger log = LoggerFactory.getLogger(ConcertCategoryService.class);
    private final IConcertCategoryDao dao;
    private final ConversionService conversionService;

    public ConcertCategoryService(IConcertCategoryDao dao, ConversionService conversionService) {
        this.dao = dao;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "concertCategory")
    public ConcertCategory save(@Valid SaveConcertCategoryDto categoryDto) {
        ConcertCategory concertCategory = conversionService.convert(categoryDto, ConcertCategory.class);
        log.info("SAVE CATEGORY - {}", concertCategory);
        return dao.save(concertCategory);
    }

    @Override
    @Cacheable(cacheNames = "concertCategory")
    public Page<ConcertCategory> getAll(Pageable page) {
        Page<ConcertCategory> all = dao.findAll(page);
        log.info("GET PAGE OF CONCERTS, ELEMENTS - {}", all.getTotalElements());
        return all;
    }

    @Override
    @Cacheable(cacheNames = "concertCategory")
    public ConcertCategory get(UUID uuid) {
        ConcertCategory category = dao.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
        log.info("GET CATEGORY - {} ", category);
        return category;

    }
}
