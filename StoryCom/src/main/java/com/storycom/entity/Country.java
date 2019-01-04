package com.storycom.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "COUNTRIES")
public class Country {

    @Id
    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @Column(name = "COUNTRY_FULL")
    private String countryFull;
}
