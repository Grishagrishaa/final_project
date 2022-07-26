package com.example.classifier.service;

import com.example.classifier.dao.api.IConcertCategoryDao;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.service.api.IClassifierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConcertCategoryService implements IClassifierService<ConcertCategory> {
    private static final Logger log = LoggerFactory.getLogger(ConcertCategoryService.class);
    private final IConcertCategoryDao dao;

    public ConcertCategoryService(IConcertCategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(ConcertCategory category) {
        dao.save(category);
        log.info("SAVE CATEGORY " + category);
    }

    @Override
    public Page<ConcertCategory> getAll(Pageable page) {
        Page<ConcertCategory> all = dao.findAll(page);
        log.info("GET PAGE OF CONCERTS, ELEMENTS - " + all.getTotalElements());
        return all;
    }

    @Override
    public ConcertCategory get(UUID uuid) {
        ConcertCategory category = dao.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
        log.info("GET CATEGORY: " + category);
        return category;

    }
}
