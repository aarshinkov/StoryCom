package com.safb.storycom.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoggedInUser extends User
{
  private Integer userId;
  private String email;
  private String firstName;
  private String lastName;

  public LoggedInUser(String username, String password, String email, boolean enabled,
          boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
          Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, Integer userId)
  {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    this.userId = userId;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFullName()
  {
    if (lastName == null || "".equalsIgnoreCase(lastName))
    {
      return firstName;
    }
    return firstName + " " + lastName;
  }

  public Integer getUserId()
  {
    return userId;
  }

  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }
}
