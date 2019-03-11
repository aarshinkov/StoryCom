package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetail {

    @Id
    @Column(name = "user_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
