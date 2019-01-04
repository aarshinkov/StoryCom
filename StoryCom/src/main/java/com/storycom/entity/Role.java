package com.storycom.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {

    @Id
    @Column(name = "ROLENAME", nullable = false)
    @Size(min = 1, max = 50)
    @NotNull
    private String rolename;

    @Column(name = "DESCR")
    @Size(max = 300)
    private String descr;

    @Column(name = "DISPLAY_NAME")
    private String displayName;
}

