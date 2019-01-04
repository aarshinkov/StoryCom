package com.storycom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "S_USERS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
    private Integer userId;

    @Column(name = "USERNAME")
    @Size(min = 4, max = 50)
    @NotNull
    private String username;

    @Column(name = "FIRST_NAME")
    @Size(min = 4, max = 100)
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
//    @Size(min = 4, max = 100)
//    @NotNull
    private String lastName;

    @Column(name = "PASSWORD")
    @Size(min = 4, max = 100)
    @JsonIgnore
    private String password;

    @Column(name = "EMAIL")
    @Size(max = 200)
    @Email
    @NotEmpty
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_DETAIL_ID")
    private UserDetail userDetail;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLENAME"))
    private List<Role> roles = new ArrayList<>();

    public String getUserFullName() {
        if (lastName == null || "".equalsIgnoreCase(lastName)) {
            return firstName;
        }
        return firstName + " " + lastName;
    }
}
