package com.safb.storycom.services;

import com.safb.storycom.entity.*;

public interface ProfileService
{
  void savePersonalInfo(UserEntity user);

  void saveDetails(UserEntity user);
}
