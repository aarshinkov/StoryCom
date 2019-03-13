package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetail
{

  @Id
  @Column(name = "user_detail_id")
  @SequenceGenerator(name = "SEQ_GEN_USER_DETAILS", sequenceName = "S_USER_DETAILS", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER_DETAILS")
  private Integer userDetailId;

  @Column(name = "gender")
  private String gender;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "country_name")
  private Country country;

  @Column(name = "facebook")
  private String facebook;

  @Column(name = "twitter")
  private String twitter;

  @Column(name = "youtube")
  private String youtube;

  @Column(name = "instagram")
  private String instagram;
}
