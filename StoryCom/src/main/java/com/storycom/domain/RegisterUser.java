package com.storycom.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterUser {
    @NotEmpty(message = "You have not filled in your first name")
    @Size(max = 100)
    private String firstName;

    @NotEmpty(message = "You have not filled in your last name")
    @Size(max = 100)
    private String lastName;

    @NotEmpty(message = "You have not provided an email")
    @Size(max = 200)
    @Email
    private String email;

    @NotEmpty(message = "You have not specified an username")
    @Size(max = 50)
    private String username;

    @NotEmpty(message = "You have not entered a password")
    @Size(min = 4, max = 100)
    private String password;

    @NotEmpty(message = "You have not entered a password")
    @Size(min = 4, max = 100)
    private String confirmPassword;

    public RegisterUser() {
    }

    @Override
    public String toString() {
        return "firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", username=" + username +
                ", password=" + password +
                ", confirmPassword='" + confirmPassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
