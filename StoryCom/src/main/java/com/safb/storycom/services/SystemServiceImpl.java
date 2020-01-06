package com.safb.storycom.services;

import com.safb.storycom.entity.*;
import com.safb.storycom.repository.*;
import com.safb.storycom.utils.*;
import java.util.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class SystemServiceImpl implements SystemService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private CountriesRepository countriesRepository;

  @Override
  public List<CountryEntity> getAllCountries(HttpSession session)
  {
    if (session.getAttribute(SessionAttributes.COUNTRIES) != null)
    {
      return (List<CountryEntity>) session.getAttribute(SessionAttributes.COUNTRIES);
    }

    List<CountryEntity> countries = countriesRepository.findAll();

    //TODO uncomment when necessary
//    session.setAttribute(SessionAttributes.COUNTRIES, countries);

    return countries;
  }
}
