package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import com.safb.storycom.repository.*;
import java.sql.*;
import java.util.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

@Service
public class StoriesServiceImpl implements StoriesService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private HttpSession session;

  @Autowired
  private StoriesRepository storiesRepository;

  @Override
  public List<StoryEntity> getAllStories()
  {
    if (session.getAttribute("stories") != null)
    {
      return (List<StoryEntity>) session.getAttribute("stories");
    }

    return storiesRepository.findAll();
  }

  @Override
  public void addStory(StoryEntity story, UserEntity user)
  {
    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_STORIES.INSERT_STORY(?,?,?,?)}");
      cstmt.setString(1, story.getTitle());
      cstmt.setString(2, story.getOverview());
      cstmt.setString(3, story.getContent());
      cstmt.setInt(4, user.getUserId());

      cstmt.execute();
      log.debug("Story added successfully!");
    }
    catch (SQLException e)
    {
      log.error("Error adding story to database!");
    }
  }

  @Override
  public void updateStoriesViews(Integer storyId)
  {
    try
    {
      Integer storyViews = storiesRepository.findByStoryId(storyId).getViews();

      String sqlUpd = "UPDATE STORIES SET VIEWS = ? WHERE STORY_ID = ?";

      storyViews++;

      jdbcTemplate.update(sqlUpd, storyViews, storyId);

    }
    catch (Exception e)
    {
      log.debug("Error updating story's views!");
    }
  }

  @Override
  public boolean editStory(StoryEntity story)
  {
    try
    {
      CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_STORIES.UPDATE_STORY(?,?,?,?)}");
      cstmt.setInt(1, story.getStoryId());
      cstmt.setString(2, story.getTitle());
      cstmt.setString(3, story.getOverview());
      cstmt.setString(4, story.getContent());

      cstmt.execute();
      log.debug("Story with id: " + story.getStoryId() + " has been updated successfully");
      return true;
    }
    catch (SQLException e)
    {
      log.error("An error occured during editing story with id: " + story.getStoryId(), e);
      return false;
    }
  }

  @Override
  public boolean deleteStory(Integer storyId)
  {
    try
    {

      String sql = "DELETE FROM STORIES WHERE STORY_ID = ?";

      PreparedStatement stmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareStatement(sql);
      stmt.setInt(1, storyId);

      stmt.execute();

      log.debug("Story with id: " + storyId + " has been successfully deleted.");
      return true;
    }
    catch (Exception e)
    {
      log.error("Error occured during deleting story with id: " + storyId, e);
      return false;
    }
  }
}
