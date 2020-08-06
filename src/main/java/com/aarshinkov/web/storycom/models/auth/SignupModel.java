package com.aarshinkov.web.storycom.models.auth;

import java.io.*;
import javax.validation.constraints.*;
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
public class SignupModel implements Serializable
{
  @NotEmpty
  @Size(min = 2, max = 100)
  private String firstName;

//  @NotEmpty
//  @Size(min = 2, max = 100)
  private String lastName;

  @NotEmpty
  @Email
  @Size(max = 200)
  private String email;

  @NotEmpty
  @Size(min = 2, max = 100)
  private String password;

  @NotEmpty
  @Size(min = 2, max = 100)
  private String confirmPassword;
}
