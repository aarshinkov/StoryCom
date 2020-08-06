package com.aarshinkov.web.storycom.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
@RequestMapping(value = "/test")
public class TestController
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping(value = "/passwordEncode/{password}")
  @ResponseBody
  public String passwordEncode(@PathVariable(value = "password") String password)
  {
    return passwordEncoder.encode(password);
  }

  @GetMapping(value = "/test1")
  @ResponseBody
  @PreAuthorize("@expressions.isUserOwner(1, 2)")
  public String test1()
  {
    LOG.debug("test1");
    return "test1";
  }
}
