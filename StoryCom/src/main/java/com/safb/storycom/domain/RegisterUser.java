package com.safb.storycom.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser
{
  @NotEmpty(message = "You have not provided an email")
  @Size(max = 200)
  @Email
  private String email;

  @NotEmpty(message = "You have not entered a password")
  @Size(min = 4, max = 100)
  private String password;

  @NotEmpty(message = "You have not filled in your first name")
  @Size(max = 100)
  private String firstName;

  @NotEmpty(message = "You have not filled in your last name")
  @Size(max = 100)
  private String lastName;

  @NotEmpty(message = "You have not entered a password")
  @Size(min = 4, max = 100)
  private String confirmPassword;
}
