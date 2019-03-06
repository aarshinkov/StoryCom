package com.storycom.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements Serializable {

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
}

