package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import java.util.*;
import javax.servlet.http.*;

public interface SystemService
{
  List<CountryEntity> getAllCountries(HttpSession session);
}
