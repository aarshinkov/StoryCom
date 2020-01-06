package com.safb.storycom.entity;

import java.io.*;
import java.sql.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stories")
public class StoryEntity implements Serializable
{
  @Id
  @Column(name = "story_id")
  @SequenceGenerator(name = "seq_gen_story", sequenceName = "s_stories", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_story")
  private Integer storyId;

  @Column(name = "title")
  private String title;

  @Column(name = "overview")
  private String overview;

  @Column(name = "content")
  private String content;

  @Column(name = "comments")
  private Integer comments;

  @Column(name = "views")
  private Integer views;

  @Column(name = "likes")
  private Integer likes;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
