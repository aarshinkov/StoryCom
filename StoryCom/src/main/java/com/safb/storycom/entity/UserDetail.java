package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetail implements Serializable
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

  public Integer getUserDetailId()
  {
    return userDetailId;
  }

  public void setUserDetailId(Integer userDetailId)
  {
    this.userDetailId = userDetailId;
  }

  public String getGender()
  {
    return gender;
  }

  public void setGender(String gender)
  {
    this.gender = gender;
  }

  public Country getCountry()
  {
    return country;
  }

  public void setCountry(Country country)
  {
    this.country = country;
  }

  public String getFacebook()
  {
    return facebook;
  }

  public void setFacebook(String facebook)
  {
    this.facebook = facebook;
  }

  public String getTwitter()
  {
    return twitter;
  }

  public void setTwitter(String twitter)
  {
    this.twitter = twitter;
  }

  public String getYoutube()
  {
    return youtube;
  }

  public void setYoutube(String youtube)
  {
    this.youtube = youtube;
  }

  public String getInstagram()
  {
    return instagram;
  }

  public void setInstagram(String instagram)
  {
    this.instagram = instagram;
  }
}
