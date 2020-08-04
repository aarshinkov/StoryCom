package com.aarshinkov.web.storycom.entities;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
@Builder
@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
public class UserEntity implements UserDetails, Serializable
{
  @Id
  @SequenceGenerator(name = "seq_gen_user", sequenceName = "s_users", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_user")
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "created_on")
  private Timestamp createdOn;
  
  @Column(name = "edited_on")
  private Timestamp editedOn;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rolename"))
  private List<RoleEntity> roles;

  @Override
  public String toString()
  {
    return "UserEntity{" + "userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + '}';
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (RoleEntity role : roles)
    {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
    }

    return authorities;
  }

  public String getFullName()
  {
    return (lastName != null) ? firstName + ' ' + lastName : firstName;
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
