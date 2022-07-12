package com.example.classifier.service;

import com.example.classifier.dao.api.IConcertCategoryDao;
import com.example.classifier.dao.entity.ConcertCategory;
import com.example.classifier.dto.SaveConcertCategoryDto;
import com.example.classifier.service.api.IClassifierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConcertCategoryService implements IClassifierService<ConcertCategory> {
    private final IConcertCategoryDao dao;

    public ConcertCategoryService(IConcertCategoryDao dao) {
        this.dao = dao;
    }

    @Override
    public void save(ConcertCategory category) {
        category.setCreateDate(LocalDateTime.now());
        dao.save(category);
    }

    @Override
    public Page<ConcertCategory> getAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public ConcertCategory get(UUID uuid) {
        return dao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("INCORRECT UUID, WASN'T FOUND"));
    }
}
