package com.aarshinkov.web.storycom.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserEntity implements Serializable
{
  public long userId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Timestamp createdOn;
}
