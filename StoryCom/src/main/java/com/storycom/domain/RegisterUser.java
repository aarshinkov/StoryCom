package com.storycom.domain;

public class RegisterUser {
    private String name;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public RegisterUser() {
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", email=" + email +
                ", username=" + username +
                ", password=" + password +
                ", confirmPassword='" + confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
