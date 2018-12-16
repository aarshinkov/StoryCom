package com.storycom.services;

import com.storycom.domain.Password;
import com.storycom.domain.RegisterUser;
import com.storycom.entity.User;
import com.storycom.security.StoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    public User registerUserToUser(RegisterUser registerUser) {

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setFirstName(registerUser.getFirstName());
        user.setLastName(registerUser.getLastName());
        user.setPassword(registerUser.getPassword());
        user.setEmail(registerUser.getEmail());

        return user;
    }

    @Override
    public void registerUser(User user) {
        log.debug("Registering new user...");

        try {
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.INSERT_NORMAL_USER(?, ?, ?, ?, ?)}");
            cstmt.setString(1, user.getUsername());
            cstmt.setString(2, user.getFirstName());
            cstmt.setString(3, user.getLastName());
            cstmt.setString(4, passwordEncoder.encode(user.getPassword()));
            cstmt.setString(5, user.getEmail());

            cstmt.execute();
            log.debug("User registered successfully!");
        } catch (Exception e) {
            log.error("Error register the user!");
        }
    }

    @Override
    public void changePassword(StoryUser storyUser, Password password) {
        log.debug("Changing password for user with id: " + storyUser.getUserId());

        String sql = "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?";

        try {
            CallableStatement cstm = dataSource.getConnection().prepareCall(sql);
            cstm.setString(1, password.getEncodedPassword());
            cstm.setInt(2, storyUser.getUserId());

            cstm.executeUpdate();
            log.debug("Password updated successfully!");
        } catch (SQLException e) {
            log.debug("Error updating password");
//            e.printStackTrace();
        }
    }
}
