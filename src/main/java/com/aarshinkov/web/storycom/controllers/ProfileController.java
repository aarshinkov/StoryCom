package com.aarshinkov.web.storycom.controllers;

import com.aarshinkov.web.storycom.base.*;
import com.aarshinkov.web.storycom.dto.*;
import com.aarshinkov.web.storycom.models.users.*;
import com.aarshinkov.web.storycom.services.*;
import javax.servlet.http.*;
import javax.validation.*;
import org.modelmapper.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 2.0.0
 */
@Controller
public class ProfileController extends Base
{
  private final Logger LOG = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService userService;

  @Autowired
  private SystemService systemService;

  @Autowired
  private StoryService storyService;

  @Autowired
  private ModelMapper mapper;

  @GetMapping(value = "/profile")
  public String profile(HttpServletRequest request, Model model)
  {
    long userId = (Long) systemService.getSessionAttribute(request, "userId");
    UserDto user = userService.getUserByUserId(userId);

    model.addAttribute("user", user);
    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "profile");

    return "profile/profile";
  }

  @GetMapping(value = "/profile/edit")
  public String prepareProfileEdit(HttpServletRequest request, Model model)
  {
    long userId = (Long) systemService.getSessionAttribute(request, "userId");
    UserDto userDto = userService.getUserByUserId(userId);

    UserEditModel uem = new UserEditModel();

    mapper.map(userDto, uem);

    model.addAttribute("uem", uem);
    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "profile");

    return "profile/edit";
  }

  @PostMapping(value = "/profile/edit")
  public String editProfile(@ModelAttribute("uem") @Valid UserEditModel uem, BindingResult bindingResult,
          RedirectAttributes redirectAttributes, HttpServletRequest request, Model model)
  {
    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "profile");
      model.addAttribute("submenu", "profile");

      return "profile/edit";
    }

    try
    {
      UserDto updatedUser = userService.updateUser(uem);

      systemService.changeLoggerUserInfo(request, updatedUser);

      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("profile.edit.success"));
    }
    catch (Exception e)
    {
      redirectAttributes.addFlashAttribute("msgError", getMessage("profile.edit.error"));
    }

    return "redirect:/profile";
  }

//  @GetMapping(value = "/stories/my")
//  public String myStories(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
//          @RequestParam(value = "limit", defaultValue = "5", required = false) Integer limit,
//          HttpServletRequest request, Model model)
//  {
//    model.addAttribute("globalMenu", "profile");
//    model.addAttribute("submenu", "mystories");
//
//    long userId = (Long) systemService.getSessionAttribute(request, "userId");
//
//    List<StoryDto> stories = storyService.getStories(page, limit, null, userId);
//
//    model.addAttribute("storiesCount", stories.size());
//
//    model.addAttribute("stories", stories);
//
//    int start = (page - 1) * limit + 1;
//    Long globalCount = storyService.getStoriesCountByUser(userId);
//    int end = start + stories.size() - 1;
//
//    Page pageWrapper = new PageImpl();
//    pageWrapper.setCurrentPage(page);
//    pageWrapper.setMaxElementsPerPage(limit);
//    pageWrapper.setStartPage(start);
//    pageWrapper.setEndPage(end);
//    pageWrapper.setLocalTotalElements(new Long(stories.size()));
//    pageWrapper.setGlobalTotalElements(globalCount);
//
//    model.addAttribute("pageWrapper", pageWrapper);
//    model.addAttribute("maxPagesPerView", 5);
//
//    return "profile/stories";
//  }

  @GetMapping(value = "/settings")
  public String prepareSettings(Model model)
  {
    ChangePasswordModel cpm = new ChangePasswordModel();

    model.addAttribute("cpm", cpm);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "settings");

    return "profile/settings";
  }

  @PostMapping(value = "/changePassword")
  public String changePassword(@ModelAttribute("cpm") @Valid ChangePasswordModel cpm, BindingResult bindingResult,
          RedirectAttributes redirectAttributes, Model model)
  {
    if (!cpm.getNewPassword().equals(cpm.getConfirmPassword()))
    {
      bindingResult.rejectValue("newPassword", "errors.password.nomatch");
      bindingResult.rejectValue("confirmPassword", "errors.password.nomatch");
    }

    boolean isPasswordMatch = userService.isPasswordMatch(cpm.getUserId(), cpm.getCurrentPassword());

    if (!isPasswordMatch)
    {
      bindingResult.rejectValue("currentPassword", "errors.password.current");
    }

    if (bindingResult.hasErrors())
    {
      model.addAttribute("globalMenu", "profile");
      model.addAttribute("submenu", "settings");

      return "profile/settings";
    }

    try
    {

      UserDto updatedUser = userService.changePassword(cpm);
      redirectAttributes.addFlashAttribute("msgSuccess", getMessage("profile.settings.pass.success"));
    }
    catch (Exception e)
    {
      redirectAttributes.addFlashAttribute("msgError", getMessage("profile.settings.pass.error"));
    }

    return "redirect:/profile";
  }
}
