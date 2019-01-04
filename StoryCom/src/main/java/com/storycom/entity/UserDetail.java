package com.storycom.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "USER_DETAILS")
public class UserDetail {

    @Id
    @Column(name = "USER_DETAIL_ID")
    @SequenceGenerator(name = "SEQ_GEN_USER_DETAIL", sequenceName = "S_USER_DETAILS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER_DETAIL")
    private Integer userDetailId;

    @Column(name = "GENDER")
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COUNTRY_NAME")
    private Country country;

    @Column(name = "FACEBOOK")
    private String facebook;

    @Column(name = "TWITTER")
    private String twitter;

    @Column(name = "YOUTUBE")
    private String youtube;

    @Column(name = "INSTAGRAM")
    private String instagram;
}
