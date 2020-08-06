package com.aarshinkov.web.storycom.security;

import com.aarshinkov.web.storycom.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.access.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private AuthenticationProvider authProvider;

  @Autowired
  private AuthenticationSuccessHandler authSuccessHandler;

  @Autowired
  private SimpleUrlAuthenticationFailureHandler authFailureHandler;

  @Autowired
  private LogoutSuccessHandler logoutSuccessHandler;

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
//    http
//            .authorizeRequests()
//            .antMatchers("/", "/home").permitAll()
//            .antMatchers("/profile").authenticated()
//            .and()
//            .formLogin()
//            .loginProcessingUrl("/authentication")
//            .loginPage("/login")
//            .usernameParameter("email")
//            .passwordParameter("password")
//            .defaultSuccessUrl("/")
//            .permitAll()
//            .and()
//            .logout()
//            .logoutSuccessUrl("/login")
//            .permitAll();

    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/home").permitAll()
            .antMatchers("/profile/**", "/stories/my", "/settings", "/changePassword").authenticated()
            .antMatchers("/story/create").authenticated()
            .antMatchers("/story/edit/**").authenticated()
            .antMatchers("/story/delete").authenticated()
            .antMatchers("/story/comment/create").authenticated()
            .antMatchers("/login", "/authentication").anonymous()
            .antMatchers("/signup").anonymous()
            .antMatchers("/users/**").hasRole("ADMIN")
            .and()
            .formLogin()
            .loginProcessingUrl("/authentication")
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(authSuccessHandler)
            .and()
            .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessHandler(logoutSuccessHandler)
            .and()
            .httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }
}
