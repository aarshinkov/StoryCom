package com.safb.storycom.base;

import com.safb.storycom.entity.*;
import com.safb.storycom.repository.*;
import com.safb.storycom.utils.*;
import java.text.*;
import javax.annotation.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.i18n.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;

public class Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Resource(name = "messageSource")
  private MessageSource messageSource;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private HttpSession session;

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

  protected UserEntity getLoggedUser()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    try
    {
      return (UserEntity) auth.getPrincipal();
    }
    catch (ClassCastException ex)
    {
      log.debug("No logged user has been found!");
      return null;
    }
    catch (Exception e)
    {
      log.error("Error getting LoggedUser!", e);
      return null;
    }
  }

  protected Integer getLoggedUserId()
  {
    return (Integer) session.getAttribute("userId");
  }

  protected UserEntity getUser()
  {
    if (session.getAttribute(SessionAttributes.LOADED_USER) != null)
    {
      return (UserEntity) session.getAttribute(SessionAttributes.LOADED_USER);
    }

    UserEntity user = usersRepository.findByUserId(getLoggedUser().getUserId());
    session.setAttribute(SessionAttributes.LOADED_USER, user);

    return user;
  }
}
