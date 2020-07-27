package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.repositories.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Service
public class UserServiceImpl implements UserService
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
  {
    UserEntity userEntity = usersRepository.findByEmail(email);

    if (userEntity == null)
    {
      throw new UsernameNotFoundException(email);
    }

    List<GrantedAuthority> roles = new ArrayList<>();

    for (RoleEntity role : userEntity.getRoles())
    {
      roles.add(new SimpleGrantedAuthority(role.getRolename()));
    }

    return userEntity;
  }
}
