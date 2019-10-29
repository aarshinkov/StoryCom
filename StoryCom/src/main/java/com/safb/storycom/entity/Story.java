package com.safb.storycom.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stories")
public class Story implements Serializable
{
  @Id
  @Column(name = "story_id")
  @SequenceGenerator(name = "SEQ_GEN_STORY", sequenceName = "S_STORIES", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_STORY")
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

  @Transient
  private String createdOnFormatted;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  public Integer getStoryId()
  {
    return storyId;
  }

  public void setStoryId(Integer storyId)
  {
    this.storyId = storyId;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getOverview()
  {
    return overview;
  }

  public void setOverview(String overview)
  {
    this.overview = overview;
  }

  public String getContent()
  {
    return content;
  }

  public void setContent(String content)
  {
    this.content = content;
  }

  public Integer getComments()
  {
    return comments;
  }

  public void setComments(Integer comments)
  {
    this.comments = comments;
  }

  public Integer getViews()
  {
    return views;
  }

  public void setViews(Integer views)
  {
    this.views = views;
  }

  public Integer getLikes()
  {
    return likes;
  }

  public void setLikes(Integer likes)
  {
    this.likes = likes;
  }

  public Date getCreatedOn()
  {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn)
  {
    this.createdOn = createdOn;
  }

  public String getCreatedOnFormatted()
  {
    return createdOnFormatted;
  }

  public void setCreatedOnFormatted(String createdOnFormatted)
  {
    this.createdOnFormatted = createdOnFormatted;
  }

  public UserEntity getUser()
  {
    return user;
  }

  public void setUser(UserEntity user)
  {
    this.user = user;
  }
}
