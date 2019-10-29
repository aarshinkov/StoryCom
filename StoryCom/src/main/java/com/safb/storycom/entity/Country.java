package com.safb.storycom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "countries")
public class Country implements Serializable
{
  @Id
  @Column(name = "country_name")
  private String countryName;

  @Column(name = "country_full")
  private String countryFull;

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }

  public String getCountryFull()
  {
    return countryFull;
  }

  public void setCountryFull(String countryFull)
  {
    this.countryFull = countryFull;
  }
}
