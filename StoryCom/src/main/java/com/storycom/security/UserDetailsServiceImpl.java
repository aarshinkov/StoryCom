package com.storycom.security;

import com.storycom.entity.Role;
import com.storycom.entity.User;
import com.storycom.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("In loadUserByUsername");
        log.debug("Looking for user with username: " + username);

        User user = usersRepository.findUserByUsername(username);

        if (user == null) {
            log.debug("Username '" + username + "' not found in the database!");
            throw new UsernameNotFoundException("Username '" + username + "' not found in the database!");
        } else {
            log.debug("User found in the database, userId: " + user.getUserId());
            log.debug("Roles: " + user.getRoles());

            if (user.getRoles().isEmpty()) {
                throw new UsernameNotFoundException("User has no roles!");
            }
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//    String sql = "select r.rolename from user_roles r where r.user_id = ?";
//    SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, user.getUserId());

        //while (rs.next())
        for (Role role : user.getRoles()) {
//      authorities.add(new SimpleGrantedAuthority("ROLE_" + rs.getString("rolename")));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
        }

        StoryUser storyUser = new StoryUser(username, user.getPassword(),
                true, true, true, true,
                authorities, user.getFirstName(), user.getLastName(), user.getUserId());

        return storyUser;
    }
}
