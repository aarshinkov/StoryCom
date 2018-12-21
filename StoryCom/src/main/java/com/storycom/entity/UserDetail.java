package com.storycom.entity;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "UserDetail: userDetailId='" + userDetailId +
                "', gender='" + gender +
                "', country='" + country +
                "', facebook='" + facebook +
                "', twitter='" + twitter +
                "', youtube='" + youtube +
                "', instagram='" + instagram + "'";
    }

    public Integer getUserDetailId() {
        return userDetailId;
    }

    public void setUserDetailId(Integer userDetailId) {
        this.userDetailId = userDetailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
