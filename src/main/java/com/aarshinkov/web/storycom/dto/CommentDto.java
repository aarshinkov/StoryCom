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
public class CommentDto implements Serializable
{
  private Long commentId;
  private String content;
  private StoryDto story;
  private UserDto user;
  private Timestamp createdOn;
  private Timestamp editedOn;
}
