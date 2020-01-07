/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safb.storycom.dto;

import java.sql.*;
import java.util.*;
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
public class UserDto
{
  private Integer userId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private Timestamp createdOn;
  private UserDetailDto userDetail;
  private Set<RoleDto> roles;
}
