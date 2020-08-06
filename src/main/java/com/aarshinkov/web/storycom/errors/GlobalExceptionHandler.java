package com.aarshinkov.web.storycom.errors;

import org.slf4j.*;
import org.springframework.security.access.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(AccessDeniedException.class)
  public String handle403(AccessDeniedException ex)
  {
    return "redirect:/403";
  }
}
