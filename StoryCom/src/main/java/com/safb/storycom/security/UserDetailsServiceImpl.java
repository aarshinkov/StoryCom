package com.safb.storycom.security;

import com.safb.storycom.entity.RoleEntity;
import com.safb.storycom.entity.UserEntity;
import com.safb.storycom.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String identificator) throws UsernameNotFoundException
  {
    log.debug("Looking for user with username: " + identificator);

    UserEntity user = usersRepository.findByUsername(identificator);

    if (user == null)
    {
      log.debug("Username '" + identificator + "' not found in the database!");
      throw new UsernameNotFoundException("Username '" + identificator + "' not found in the database!");
    }
    else
    {
      log.debug("User found in the database, userId: " + user.getUserId());
      log.debug("Roles: " + user.getRoles());

      if (user.getRoles().isEmpty())
      {
        throw new UsernameNotFoundException("User has no roles!");
      }
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//    String sql = "select r.rolename from user_roles r where r.user_id = ?";
//    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user.getUserId());

    //while (rs.next())
    for (RoleEntity role : user.getRoles())
    {
//      authorities.add(new SimpleGrantedAuthority("ROLE_" + rs.getString("rolename")));
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
    }

    return new LoggedUser(identificator, user.getPassword(), user.getEmail(),
            true, true, true, true,
            authorities, user.getFirstName(), user.getLastName(), user.getUserId());
  }
}
