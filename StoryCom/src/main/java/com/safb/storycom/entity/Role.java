package com.safb.storycom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role implements Serializable
{
  @Id
  @Column(name = "rolename", nullable = false)
  @Size(min = 1, max = 50)
  @NotNull
  private String rolename;

  @Column(name = "descr")
  @Size(max = 300)
  private String descr;

  @Column(name = "display_name")
  private String displayName;

  public String getRolename()
  {
    return rolename;
  }

  public void setRolename(String rolename)
  {
    this.rolename = rolename;
  }

  public String getDescr()
  {
    return descr;
  }

  public void setDescr(String descr)
  {
    this.descr = descr;
  }

  public String getDisplayName()
  {
    return displayName;
  }

  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }
}
