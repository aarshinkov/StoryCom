package com.storycom.domain;

public class Password {
    private String password;
    private String hashedPassword;

    @Override
    public String toString() {
        return "password=" + password + ", hashedPassword=" + hashedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}
