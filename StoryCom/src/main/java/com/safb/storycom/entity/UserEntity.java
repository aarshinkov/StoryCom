package com.safb.storycom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class UserEntity implements Serializable
{
  @Id
  @Column(name = "user_id")
  @SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "S_USERS", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
  private Integer userId;

  @Column(name = "username")
  @Size(min = 4, max = 50)
  @NotNull
  private String username;

  @Column(name = "first_name")
  @Size(min = 4, max = 100)
  @NotNull
  private String firstName;

  @Column(name = "last_name")
//    @Size(min = 4, max = 100)
//    @NotNull
  private String lastName;

  @Column(name = "password")
  @Size(min = 4, max = 100)
  @JsonIgnore
  private String password;

  @Column(name = "email")
  @Size(max = 200)
  @Email
  @NotEmpty
  private String email;

  @Column(name = "created_on")
  private Date createdOn;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_detail_id")
  private UserDetail userDetail;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rolename"))
  private List<Role> roles = new ArrayList<>();

  public String getUserFullName()
  {
    if (lastName == null || "".equalsIgnoreCase(lastName))
    {
      return firstName;
    }
    return firstName + " " + lastName;
  }
}
