package com.aarshinkov.web.storycom;

import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

@SpringBootApplication
public class StoryComApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(StoryComApplication.class, args);
  }

  @Bean
  public PasswordEncoder passwordEncoder()
  {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public ModelMapper mapper()
  {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setSkipNullEnabled(true).setMatchingStrategy(MatchingStrategies.LOOSE);

    return mapper;
  }
}
