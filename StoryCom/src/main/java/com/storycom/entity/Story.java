package com.storycom.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STORIES")
public class Story {

    @Id
    @Column(name = "STORY_ID")
    @SequenceGenerator(name = "SEQ_GEN_STORY", sequenceName = "S_STORIES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_STORY")
    private Integer storyId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}