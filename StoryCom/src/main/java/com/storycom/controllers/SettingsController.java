package com.storycom.controllers;

import com.storycom.base.Base;
import com.storycom.domain.Password;
import com.storycom.entity.User;
import com.storycom.repository.CountriesRepository;
import com.storycom.repository.UsersRepository;
import com.storycom.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.CallableStatement;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(value = "/settings")
public class SettingsController extends Base {

    private static final String GLOBAL_MENU = "settings";

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/changepass")
    public String prepareChangePassword(Model model) {

        log.debug("Preparing change password!");

        model.addAttribute("password", new Password());

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "password");

        return "settings/changePass";
    }

    @PostMapping(value = "/changepass")
    public String changePassword(@Valid Password password, BindingResult bindingResult, Model model) {

        String sql = "SELECT U.PASSWORD FROM USERS U WHERE USER_ID = ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, getStoryUser().getUserId());

        String dbPassword = null;

        while (rs.next()) {
            dbPassword = rs.getString("password");
        }

        if (!passwordEncoder.matches(password.getCurrentPassword(), dbPassword)) {

            model.addAttribute("submenu", "password");
            model.addAttribute("globalMenu", GLOBAL_MENU);

            model.addAttribute("error", getMessage("error.password.currentpass"));

            return "settings/changePass";
        }

        if (!password.getPassword().equals(password.getConfirmPassword())) {

            model.addAttribute("submenu", "password");
            model.addAttribute("globalMenu", GLOBAL_MENU);

            model.addAttribute("error", getMessage("error.password.confirm"));

            return "settings/changePass";
        }
//
//        log.debug("Changing password for user: " + getStoryUser().getUsername());
//        log.debug("User id: " + getStoryUser().getUserId());
//
//        log.debug("Password: " + password.getPassword());
//        log.debug("Confirmed password: " + password.getConfirmPassword());
//
        password.setEncodedPassword(passwordEncoder.encode(password.getConfirmPassword()));

        userService.changePassword(getStoryUser(), password);

        return "redirect:/settings/changepass";
    }

}
