package com.example.classifier.dao.api;

import com.example.classifier.dao.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICountryDao extends JpaRepository<Country, UUID> {

}
