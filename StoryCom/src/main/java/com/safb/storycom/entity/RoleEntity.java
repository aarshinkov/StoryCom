package com.safb.storycom.entity;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable
{
  @Id
  @Column(name = "rolename")
  private String rolename;

  @Column(name = "display_name")
  private String displayName;

  @ManyToMany(mappedBy = "users")
  private Set<UserEntity> users;
}
