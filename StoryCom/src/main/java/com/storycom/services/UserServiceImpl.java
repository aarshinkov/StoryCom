package com.storycom.services;

import com.storycom.domain.RegisterUser;
import com.storycom.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUserToUser(RegisterUser registerUser) {

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setName(registerUser.getName());
        user.setPassword(registerUser.getPassword());
        user.setEmail(registerUser.getEmail());

        return user;
    }

    @Override
    public void registerUser(User user) {
        log.debug("Registering new user...");

        try {
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_GENERAL.INSERT_USER(?, ?, ?, ?)}");
            cstmt.setString(1, user.getUsername());
            cstmt.setString(2, user.getName());
            cstmt.setString(3, passwordEncoder.encode(user.getPassword()));
            cstmt.setString(4, user.getEmail());

            cstmt.execute();
            log.debug("User registered successfully!");
        } catch (Exception e) {
            log.error("Error register the user!");
        }
    }
}
