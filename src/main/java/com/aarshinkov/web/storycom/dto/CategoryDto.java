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
public class CategoryDto implements Serializable
{
  private Long categoryId;
  private String name;
  private Timestamp createdOn;
}
