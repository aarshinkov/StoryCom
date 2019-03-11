package com.storycom.security;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class StoryUser extends User {
    private Integer userId;
    private String firstName;
    private String lastName;

    public StoryUser(String username, String password, boolean enabled,
                     boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, Integer userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public String getFullName() {
        if (lastName == null || "".equalsIgnoreCase(lastName)) {
            return firstName;
        }
        return firstName + " " + lastName;
    }
}
