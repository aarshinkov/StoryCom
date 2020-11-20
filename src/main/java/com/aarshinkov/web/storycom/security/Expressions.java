package com.aarshinkov.web.storycom.security;

import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.services.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Service
public class Expressions
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private SystemService systemService;

  @Autowired
  private StoryService storyService;

  public boolean isUserOwner(Long storyId, HttpServletRequest request)
  {
    Long userId = (Long) systemService.getSessionAttribute(request, "userId");

    StoryDto storedStory = storyService.getStoryByStoryId(storyId);
    UserDto owner = storedStory.getUser();

    return owner.getUserId().equals(userId);
  }
}
