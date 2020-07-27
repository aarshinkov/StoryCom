package com.aarshinkov.web.storycom.entities;

import java.io.*;
import javax.persistence.*;
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
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable
{
  @Id
  @Column(name = "rolename")
  private String rolename;
}
