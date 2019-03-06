package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "stories")
public class Story implements Serializable {

    @Id
    @Column(name = "story_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storyId;

    @Column(name = "title")
    @NotEmpty(message = "Provide title for the story")
    @Size(max = 150, message = "The title must not be more than 150 symbols")
    private String title;

    @Column(name = "overview")
    @NotEmpty(message = "Please provide an overview of the story")
    @Size(max = 300, message = "The overview must not be more than 300 symbols")
    private String overview;

    @Column(name = "content")
    @NotEmpty(message = "The story must not be empty")
    private String content;

    @Column(name = "comments")
    private Integer comments;

    @Column(name = "views")
    private Integer views;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "created_on")
    private Date createdOn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
