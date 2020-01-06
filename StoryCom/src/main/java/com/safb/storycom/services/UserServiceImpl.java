package com.safb.storycom.services;

import com.safb.storycom.domain.*;
import com.safb.storycom.entity.*;
import com.safb.storycom.security.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

@Service
public class UserServiceImpl implements UserService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private DataSource dataSource;

  @Override
  public UserEntity registerUserToUser(RegisterUser registerUser)
  {

    UserEntity user = new UserEntity();
    user.setEmail(registerUser.getEmail());
    user.setPassword(registerUser.getPassword());
    user.setFirstName(registerUser.getFirstName());
    user.setLastName(registerUser.getLastName());

    return user;
  }

  @Override
  public void registerUser(UserEntity user)
  {
    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.INSERT_NORMAL_USER(?, ?, ?, ?, ?)}");
      cstmt.setString(1, user.getUsername());
      cstmt.setString(2, user.getFirstName());
      cstmt.setString(3, user.getLastName());
      cstmt.setString(4, passwordEncoder.encode(user.getPassword()));
      cstmt.setString(5, user.getEmail());

      cstmt.execute();
      log.debug("User registered successfully!");
    }
    catch (Exception e)
    {
      log.error("Error register the user!");
    }
  }

  @Override
  public void changePassword(LoggedUser loggedUser, Password password)
  {
    String sql = "UPDATE USERS SET PASSWORD = ? WHERE USER_ID = ?";

    try
    {
      CallableStatement cstm = dataSource.getConnection().prepareCall(sql);
      cstm.setString(1, password.getEncodedPassword());
      cstm.setInt(2, loggedUser.getUserId());

      cstm.executeUpdate();
      log.debug("Password updated successfully!");
    }
    catch (SQLException e)
    {
      log.error("Error updating password", e);
    }
  }
}
