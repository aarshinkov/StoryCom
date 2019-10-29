package com.safb.storycom.controllers;

import com.safb.storycom.base.Base;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/error")
public class ErrorController extends Base
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping(value = "/403")
  public String error403()
  {
    log.debug("Handling error 403!");

    return "error/403";
  }

  @GetMapping(value = "/404")
  public String error404()
  {
    log.debug("Handling error 404!");

    return "error/404";
  }

  @GetMapping(value = "/405")
  public String error405()
  {
    log.debug("Handling error 405!");

    return "error/405";
  }

  @GetMapping(value = "/500")
  public String error500()
  {
    log.debug("Handling error 500!");

    return "error/500";
  }
}
