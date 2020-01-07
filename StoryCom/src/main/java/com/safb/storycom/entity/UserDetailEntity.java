package com.safb.storycom.entity;

import java.io.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetailEntity implements Serializable
{
  @Id
  @Column(name = "user_detail_id")
  @SequenceGenerator(name = "seq_gen_user_details", sequenceName = "s_user_details", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_user_details")
  private Integer userDetailId;

  @Column(name = "gender")
  private String gender;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "country_name")
  private CountryEntity country;

  @Column(name = "facebook")
  private String facebook;

  @Column(name = "twitter")
  private String twitter;

  @Column(name = "youtube")
  private String youtube;

  @Column(name = "instagram")
  private String instagram;
}
