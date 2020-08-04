package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.repositories.RolesRepository;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.enums.*;
import com.aarshinkov.web.storycom.models.auth.*;
import com.aarshinkov.web.storycom.models.users.*;
import com.aarshinkov.web.storycom.repositories.*;
import java.sql.*;
import java.util.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
  public UserDto getUserByUserId(Long userId)
  {
    UserEntity storedUser = usersRepository.findByUserId(userId);
    UserDto result = new UserDto();

    mapper.map(storedUser, result);

    return result;
  }

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
  public UserDto updateUser(UserEditModel uem)
  {
    UserEntity storedUser = usersRepository.findByUserId(uem.getUserId());
    mapper.map(uem, storedUser);

    storedUser.setEditedOn(new Timestamp(System.currentTimeMillis()));

    UserEntity updatedUser = usersRepository.save(storedUser);
    UserDto result = new UserDto();

    mapper.map(updatedUser, result);

    return result;
  }

  @Override
  @Transactional
  public UserDto changePassword(ChangePasswordModel cpm)
  {
    UserEntity storedUser = usersRepository.findByUserId(cpm.getUserId());

    String encodedPassword = passwordEncoder.encode(cpm.getNewPassword());

    storedUser.setPassword(encodedPassword);

    UserEntity updatedUser = usersRepository.save(storedUser);

    UserDto result = new UserDto();

    mapper.map(updatedUser, result);

    return result;
  }

  @Override
  public boolean isPasswordMatch(Long userId, String password)
  {
    UserEntity storedUser = usersRepository.findByUserId(userId);

    return passwordEncoder.matches(password, storedUser.getPassword());
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
