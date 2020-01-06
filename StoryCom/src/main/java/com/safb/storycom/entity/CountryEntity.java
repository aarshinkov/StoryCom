package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class CountryEntity implements Serializable
{
  @Id
  @Column(name = "country_name")
  private String countryName;

  @Column(name = "country_full")
  private String countryFull;
}
