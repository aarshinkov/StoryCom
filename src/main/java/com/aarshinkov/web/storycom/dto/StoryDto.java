package com.aarshinkov.web.storycom.dto;

import java.io.*;
import java.sql.*;
import lombok.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StoryDto implements Serializable
{
  private Long storyId;
  private String title;
  private String story;
  private Double rating;
  private Long visits;
  private Integer anonymous;
  private CategoryDto category;
  private UserDto user;
  private Timestamp createdOn;
  private Timestamp editedOn;
}
