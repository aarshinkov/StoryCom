package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import com.safb.storycom.utils.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Service
public class ProfileServiceImpl implements ProfileService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private HttpSession session;

  @Override
  public void savePersonalInfo(UserEntity user)
  {
    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_INFORMATION(?,?,?,?)}");
      cstmt.setInt(1, user.getUserId());
      cstmt.setString(2, user.getFirstName());
      cstmt.setString(3, user.getLastName());
      cstmt.setString(4, user.getEmail());

      cstmt.execute();
      log.debug("User profile saved successfully!");
      
      session.removeAttribute(SessionAttributes.LOADED_USER);

      session.setAttribute(SessionAttributes.MSG_SUCCESS, "Profile saved successfully.");
    }
    catch (Exception e)
    {
      log.error("Error saving user profile!", e);
      session.setAttribute(SessionAttributes.MSG_ERROR, "Error saving profile.");
    }
  }

  @Override
  public void saveDetails(UserEntity user)
  {
    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_DETAILS(?,?,?,?,?,?,?)}");
      cstmt.setInt(1, user.getUserId());
      cstmt.setString(2, user.getUserDetail().getGender());
      cstmt.setString(3, user.getUserDetail().getCountry().getCountryName());
      cstmt.setString(4, user.getUserDetail().getFacebook());
      cstmt.setString(5, user.getUserDetail().getTwitter());
      cstmt.setString(6, user.getUserDetail().getYoutube());
      cstmt.setString(7, user.getUserDetail().getInstagram());

      cstmt.execute();
      log.debug("User details saved successfully!");

      session.removeAttribute(SessionAttributes.LOADED_USER);

      session.setAttribute(SessionAttributes.MSG_SUCCESS, "Details saved successfully.");
    }
    catch (Exception e)
    {
      log.error("Error saving user details!", e);
      session.setAttribute(SessionAttributes.MSG_ERROR, "Error saving details.");
    }
  }
}
