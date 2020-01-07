package com.safb.storycom.entity;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements Serializable, UserDetails
{
  @Id
  @Column(name = "user_id")
  @SequenceGenerator(name = "seq_gen_user", sequenceName = "s_users", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_user")
  private Integer userId;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  @JsonIgnore
  private String password;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_detail_id")
  private UserDetailEntity userDetail;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rolename"))
  private Set<RoleEntity> roles = new HashSet<>();

  public String getFullName()
  {
    if (lastName == null || "".equalsIgnoreCase(lastName))
    {
      return firstName;
    }
    return firstName + " " + lastName;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    Set<GrantedAuthority> authorities = new HashSet<>();

    for (RoleEntity role : roles)
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
    }

    return authorities;
  }

  @Override
  public String getUsername()
  {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }
}
