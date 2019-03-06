package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country implements Serializable {

    @Id
    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_full")
    private String countryFull;
}
