package com.safb.storycom.beans;

import org.modelmapper.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since
 */
@Component
public class Beans
{
  public ModelMapper mapper()
  {
    return new ModelMapper();
  }
}
