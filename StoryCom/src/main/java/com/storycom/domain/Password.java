package com.storycom.domain;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Password {

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String currentPassword;

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String password;

    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 symbols")
    private String confirmPassword;

    private String encodedPassword;
}