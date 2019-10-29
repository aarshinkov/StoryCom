package com.safb.storycom.base;

import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.repository.UsersRepository;
import com.safb.storycom.security.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.slf4j.*;

public class Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Resource(name = "messageSource")
  private MessageSource messageSource;

  @Autowired
  private UsersRepository usersRepository;

  private DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

  protected String getMessage(String key)
  {
    return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
  }

  protected String getMessage(String key, Object... params)
  {
    return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
  }

  protected boolean isDBConnectError(Throwable exc)
  {
    Throwable ex = exc;
    while (true)
    {
      if (ex != null)
      {
        log.debug("Ex = " + ex.getClass().getName());
        log.debug("Msg = " + ex.getMessage());

        if (ex.toString().toLowerCase().contains("could not open connection")
                || ex.toString().toLowerCase().contains("cannot get connection")
                || ex.toString().toLowerCase().contains("could not get connection")
                || ex.toString().toLowerCase().contains("connection is closed")
                || ex.toString().toLowerCase().contains("connection pool"))
        {
          return true;
        }
        else
        {
          ex = ex.getCause();
        }
      }
      else
      {
        return false;
      }
    }
  }

  protected LoggedInUser getStoryUser()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    try
    {
      return (LoggedInUser) auth.getPrincipal();
    }
    catch (ClassCastException ex)
    {
      log.debug("No logged user has been found!");
      return null;
    }
    catch (Exception e)
    {
      log.error("Error getting StoryUser!", e);
      return null;
    }
  }

  protected UserEntity getUser()
  {
    return usersRepository.findByUserId(getStoryUser().getUserId());
  }
}
