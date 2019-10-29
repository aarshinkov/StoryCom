package com.safb.storycom.repository;

import com.safb.storycom.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountriesRepository extends JpaRepository<Country, String>
{
  @Override
  List<Country> findAll();
}
