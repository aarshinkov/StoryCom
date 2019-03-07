package com.storycom.repository;

import com.storycom.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountriesRepository extends JpaRepository<Country, String> {
    List<Country> findAll();
}
