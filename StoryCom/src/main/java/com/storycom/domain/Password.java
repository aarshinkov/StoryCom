package com.storycom.domain;

import javax.validation.constraints.Size;

public class Password {

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String currentPassword;

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String password;

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String confirmPassword;

    private String encodedPassword;

    @Override
    public String toString() {
        return "currentPassword=" + currentPassword + ", password=" + password + ", confirmPassword=" + confirmPassword + ", encodedPassword=" + encodedPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
}