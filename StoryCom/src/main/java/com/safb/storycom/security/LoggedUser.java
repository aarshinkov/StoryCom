package com.safb.storycom.security;

import java.sql.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoggedUser
{
  private Integer userId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Timestamp createdOn;
}
