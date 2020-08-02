package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.models.stories.*;
import com.aarshinkov.web.storycom.repositories.*;
import java.sql.*;
import java.util.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.util.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Service
//@Transactional
public class StoryServiceImpl implements StoryService
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private StoriesRepository storiesRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private CategoriesRepository categoriesRepository;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<StoryDto> getStories(Integer page, Integer limit, String category)
  {
    try (Connection conn = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall("{? = call get_stories(?, ?, ?)}"))
    {
      // We must be inside a transaction for cursors to work.
      conn.setAutoCommit(false);

      cstmt.registerOutParameter(1, Types.REF_CURSOR);
      cstmt.setInt(2, page);
      cstmt.setInt(3, limit);

      if (StringUtils.isEmpty(category))
      {
////        cstmt.setNull(4, Types.VARCHAR);
        cstmt.setString(4, null);
      }
      else
      {
        cstmt.setString(4, category);
      }

      cstmt.execute();

      ResultSet rset = (ResultSet) cstmt.getObject(1);

      List<StoryDto> stories = new ArrayList<>();

      while (rset.next())
      {
        StoryDto story = new StoryDto();
        story.setStoryId(rset.getLong("story_id"));
        story.setTitle(rset.getString("title"));
        story.setStory(rset.getString("story"));
        story.setRating(rset.getDouble("rating"));
        story.setVisits(rset.getLong("visits"));
        story.setAnonymous(rset.getBoolean("anonymous"));
        story.setCreatedOn(rset.getTimestamp("created_on"));
        story.setEditedOn(rset.getTimestamp("edited_on"));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(rset.getLong("category_id"));
        categoryDto.setName(rset.getString("name"));

        UserDto userDto = new UserDto();
        userDto.setUserId(rset.getLong("user_id"));
        userDto.setFirstName(rset.getString("first_name"));
        userDto.setLastName(rset.getString("last_name"));

        story.setCategory(categoryDto);
        story.setUser(userDto);

        stories.add(story);
      }

      conn.commit();

      return stories;
    }
    catch (Exception e)
    {
      LOG.error("Error getting stories!", e);
    }

    return null;

//    List<StoryEntity> storedStories = storiesRepository.findByOrderByCreatedOnDesc();
//
//    List<StoryDto> result = new ArrayList<>();
//
//    for (StoryEntity storedStory : storedStories)
//    {
//      StoryDto story = new StoryDto();
//      mapper.map(storedStory, story);
//      result.add(story);
//    }
//
//    return result;
  }

  @Override
  @Transactional
  public StoryDto getStoryByStoryId(Long storyId)
  {
    StoryEntity storedStory = storiesRepository.findByStoryId(storyId);
    if (storedStory == null)
    {
      return null;
    }

    StoryDto story = new StoryDto();

    mapper.map(storedStory, story);

    return story;
  }

  @Override
  public StoryDto createStory(StoryCreateModel scm, Long userId)
  {
    UserEntity author = usersRepository.findByUserId(userId);
    CategoryEntity category = categoriesRepository.findByCategoryId(scm.getCategoryId());

    StoryEntity createStory = new StoryEntity();
    createStory.setTitle(scm.getTitle());
    createStory.setStory(scm.getStory());
    createStory.setAnonymous(scm.getAnonymous());
//    mapper.map(scm, createStory);

    createStory.setCategory(category);
    createStory.setUser(author);

    StoryEntity savedStory = storiesRepository.save(createStory);

    StoryDto result = new StoryDto();

    mapper.map(savedStory, result);

    return result;
  }

  @Override
  @Transactional
  public StoryDto updateStory(Long storyId, StoryEditModel sem)
  {
    StoryEntity story = storiesRepository.findByStoryId(storyId);

    CategoryEntity category = categoriesRepository.findByCategoryId(sem.getCategoryId());

    story.setTitle(sem.getTitle());
    story.setStory(sem.getStory());
    story.setAnonymous(sem.getAnonymous());
    story.setCategory(category);
    story.setEditedOn(new Timestamp(System.currentTimeMillis()));
    StoryEntity updatedStory = storiesRepository.save(story);

    StoryDto result = new StoryDto();

    mapper.map(updatedStory, result);

    return result;
  }

  @Override
  public StoryDto deleteStory(Long storyId)
  {
    StoryEntity storedStory = storiesRepository.findByStoryId(storyId);

    storiesRepository.delete(storedStory);

    StoryDto result = new StoryDto();
    mapper.map(storedStory, result);

    return result;
  }

  @Override
  public void readStory(Long storyId)
  {
    StoryEntity story = storiesRepository.findByStoryId(storyId);
    story.setVisits(story.getVisits() + 1);

    storiesRepository.save(story);
  }

  @Override
  public Long getStoriesCountByCategory(Long categoryId)
  {
    CategoryEntity category = categoriesRepository.findByCategoryId(categoryId);
    return storiesRepository.countByCategory(category);
  }

  @Override
  public Long getStoriesCount()
  {
    return storiesRepository.count();
  }
}
