package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.repositories.RolesRepository;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.enums.*;
import com.aarshinkov.web.storycom.models.auth.*;
import com.aarshinkov.web.storycom.repositories.*;
import java.util.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
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
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public UserDto createUser(SignupModel signup)
  {
    UserEntity user = new UserEntity();

    mapper.map(signup, user);

    String encodedPassword = passwordEncoder.encode(signup.getPassword());

    user.setPassword(encodedPassword);

    List<RoleEntity> roles = new ArrayList<>();

    roles.add(rolesRepository.findByRolename(Roles.USER.getValue()));

    user.setRoles(roles);

    UserEntity storedUser = usersRepository.save(user);

    UserDto result = new UserDto();

    mapper.map(storedUser, result);

    return result;
  }

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
