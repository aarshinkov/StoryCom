package com.storycom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_DETAIL_ID")
    private UserDetails userDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLENAME"))
    private List<Role> roles = new ArrayList<>();

    @Override
    public String toString() {
        return "userId=" + userId + ", username=" + username + ", firstName=" + firstName + ", lastName = " + lastName + ", password=" + password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
