package com.aarshinkov.web.storycom.services;

import com.aarshinkov.web.storycom.collections.*;
import com.aarshinkov.web.storycom.repositories.RolesRepository;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.entities.*;
import com.aarshinkov.web.storycom.enums.*;
import com.aarshinkov.web.storycom.models.auth.*;
import com.aarshinkov.web.storycom.models.users.*;
import com.aarshinkov.web.storycom.repositories.*;
import com.aarshinkov.web.storycom.utils.*;
import java.sql.*;
import java.util.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.thymeleaf.util.*;

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

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public UserDto getUserByUserId(Long userId)
  {
    UserEntity storedUser = usersRepository.findByUserId(userId);
    UserDto result = new UserDto();

    mapper.map(storedUser, result);

    return result;
  }

  @Override
  public ObjCollection<UserDto> getUsers(Integer page, Integer limit)
  {
    try (Connection conn = jdbcTemplate.getDataSource().getConnection();
            CallableStatement cstmt = conn.prepareCall("{? = call get_users(?, ?, ?)}");
            CallableStatement cstmtRoles = conn.prepareCall("{? = call get_user_roles(?)}"))
    {
      // We must be inside a transaction for cursors to work.
      conn.setAutoCommit(false);

      cstmt.setInt(1, page);
      cstmt.setInt(2, limit);

      cstmt.registerOutParameter(3, Types.BIGINT);
      cstmt.registerOutParameter(4, Types.REF_CURSOR);

      cstmt.execute();

      Long globalCount = (Long) cstmt.getLong(3);
      ResultSet rset = (ResultSet) cstmt.getObject(4);

      ObjCollection<UserDto> collection = new UsersCollection();

      while (rset.next())
      {
        UserDto user = new UserDto();

        user.setUserId(rset.getLong("user_id"));
        user.setEmail(rset.getString("email"));
        user.setFirstName(rset.getString("first_name"));
        user.setLastName(rset.getString("last_name"));
        user.setCreatedOn(rset.getTimestamp("created_on"));
        user.setEditedOn(rset.getTimestamp("edited_on"));

        cstmtRoles.setLong(1, user.getUserId());

        cstmtRoles.registerOutParameter(2, Types.REF_CURSOR);

        cstmtRoles.execute();

        ResultSet rsetRoles = (ResultSet) cstmtRoles.getObject(2);

        List<RoleDto> roles = new ArrayList();

        while (rsetRoles.next())
        {
          RoleDto role = new RoleDto();
          role.setRolename(rsetRoles.getString("rolename"));

          roles.add(role);
        }

        user.setRoles(roles);

        collection.getCollection().add(user);
      }

      long collectionCount = collection.getCollection().size();

      int start = (page - 1) * limit + 1;
      int end = start + collection.getCollection().size() - 1;

      Page pageWrapper = new PageImpl();
      pageWrapper.setCurrentPage(page);
      pageWrapper.setMaxElementsPerPage(limit);
      pageWrapper.setStartPage(start);
      pageWrapper.setEndPage(end);
      pageWrapper.setLocalTotalElements(collectionCount);
      pageWrapper.setGlobalTotalElements(globalCount);

      collection.setPage(pageWrapper);
//
      conn.commit();
//
      return collection;
    }
    catch (Exception e)
    {
      LOG.error("Error getting users!", e);
    }

    return null;
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

    if (StringUtils.isEmpty(uem.getLastName()))
    {
      storedUser.setLastName(null);
    }

    UserEntity updatedUser = usersRepository.save(storedUser);
    UserDto result = new UserDto();

    mapper.map(updatedUser, result);

    return result;
  }

  @Override
  @Transactional
  public UserDto deleteUser(Long userId) throws Exception
  {
    UserEntity storedUser = usersRepository.findByUserId(userId);

    if (storedUser == null)
    {
      throw new Exception("User not found");
    }

    UserDto result = new UserDto();

    mapper.map(storedUser, result);

    usersRepository.delete(storedUser);

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
