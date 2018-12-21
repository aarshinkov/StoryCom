package com.storycom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRIES")
public class Country {

    @Id
    @Column(name = "COUNTRY_NAME")
    private String countryName;

    @Column(name = "COUNTRY_FULL")
    private String countryFull;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFull() {
        return countryFull;
    }

    public void setCountryFull(String countryFull) {
        this.countryFull = countryFull;
    }
}
