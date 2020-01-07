package com.safb.storycom.dto;

import java.sql.*;
import lombok.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 3.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoryDto
{
  private Integer storyId;
  private String title;
  private String overview;
  private String content;
  private Integer comments;
  private Integer views;
  private Integer likes;
  private Timestamp createdOn;
  private UserDto user;
}
