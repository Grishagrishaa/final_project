package com.example.classifier.dao.api;

import com.example.classifier.dao.entity.ConcertCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IConcertCategoryDao extends JpaRepository<ConcertCategory, UUID> {

}
