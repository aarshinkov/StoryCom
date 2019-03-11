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
    private UsersRepository usersRepository;

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping(value = "/profile")
    public String viewProfile(Model model) {

        User user = getUser();

        log.debug(user.toString());

        model.addAttribute("user", user);

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "profile");

        return "settings/profile";
    }

    @PostMapping(value = "/profile")
    public String saveProfile(User user, Model model) {

        log.debug("Saving user profile...");

        user.setUserId(getStoryUser().getUserId());

        log.debug("User id: " + user.getUserId());

        try {
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_INFORMATION(?,?,?,?)}");
            cstmt.setInt(1, user.getUserId());
            cstmt.setString(2, user.getFirstName());
            cstmt.setString(3, user.getLastName());
            cstmt.setString(4, user.getEmail());

            cstmt.execute();
            log.debug("User profile saved successfully!");
        } catch (Exception e) {
            log.error("Error saving user profile!", e);
        }

        return "redirect:/settings/profile";
    }

    @GetMapping(value = "/details")
    public String viewDetails(Model model) {

        User user = getUser();

        model.addAttribute("user", user);
        model.addAttribute("countries", countriesRepository.findAll());

        model.addAttribute("globalMenu", GLOBAL_MENU);
        model.addAttribute("submenu", "details");

        return "settings/details";
    }

    @PostMapping(value = "/details")
    public String saveDetails(User user, Model model) {
        log.debug("Saving user details...");

        user.setUserId(getStoryUser().getUserId());

        try {
            CallableStatement cstmt = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().prepareCall("{call STORYCOM_USERS.UPDATE_USER_DETAILS(?,?,?,?,?,?,?)}");
            cstmt.setInt(1, user.getUserId());
            cstmt.setString(2, user.getUserDetail().getGender());
            cstmt.setString(3, user.getUserDetail().getCountry().getCountryName());
            cstmt.setString(4, user.getUserDetail().getFacebook());
            cstmt.setString(5, user.getUserDetail().getTwitter());
            cstmt.setString(6, user.getUserDetail().getYoutube());
            cstmt.setString(7, user.getUserDetail().getInstagram());

            cstmt.execute();
            log.debug("User details saved successfully!");
        } catch (Exception e) {
            log.error("Error saving user details!", e);
        }

        return "redirect:/settings/details";
    }

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
