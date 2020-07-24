package com.aarshinkov.web.storycom.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "stories")
@DynamicInsert
@DynamicUpdate
public class StoryEntity implements Serializable
{
  @Id
  @SequenceGenerator(name = "seq_gen_story", sequenceName = "s_stories", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_story")
  @Column(name = "story_id")
  private long storyId;

  @Column(name = "title")
  private String title;

  @Column(name = "story")
  private String story;

  @Column(name = "rating")
  private double rating;

  @Column(name = "visits")
  private long visits;

  @Column(name = "anonymous")
  private int anonymous;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "edited_on")
  private Timestamp editedOn;
//    private UserEntity user;
}
