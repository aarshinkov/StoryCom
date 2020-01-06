package com.safb.storycom.repository;

import com.safb.storycom.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountriesRepository extends JpaRepository<CountryEntity, String>
{
  @Override
  List<CountryEntity> findAll();
}
