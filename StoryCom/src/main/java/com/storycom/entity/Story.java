package com.storycom.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "STORIES")
public class Story {

    @Id
    @Column(name = "STORY_ID")
    @SequenceGenerator(name = "SEQ_GEN_STORY", sequenceName = "S_STORIES", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_STORY")
    private Integer storyId;

    @Column(name = "TITLE")
    @NotEmpty(message = "Provide title for the story")
    @Size(max = 150, message = "The title must not be more than 150 symbols")
    private String title;

    @Column(name = "OVERVIEW")
    @NotEmpty(message = "Please provide an overview of the story")
    @Size(max = 300, message = "The overview must not be more than 300 symbols")
    private String overview;

    @Column(name = "CONTENT")
    @NotEmpty(message = "The story must not be empty")
    private String content;

    @Column(name = "COMMENTS")
    private Integer comments;

    @Column(name = "VIEWS")
    private Integer views;

    @Column(name = "LIKES")
    private Integer likes;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;
}
