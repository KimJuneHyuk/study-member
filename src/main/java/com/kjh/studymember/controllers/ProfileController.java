package com.kjh.studymember.controllers;

import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.services.ProfileService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.PrinterException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

@Controller(value = "com.kjh.studymember.controllers.ProfileController")
@RequestMapping(value = "/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String postAdd(ProfileEntity profile,
                          @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user
    ) {
        Enum<? extends IResult> result;
        if (user == null) {
            result = CommonResult.FAILURE;
        } else {
            Date currenDate = new Date();
            profile.setProfileIndex(1)
                    .setUserEmail(user.getEmail())
                    .setCreatedAt(currenDate)
                    .setLastAccessAt(currenDate);
            result = this.profileService.addProfile(profile);
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
        return responseJson.toString();
    }

    @RequestMapping(value = "choose", method = RequestMethod.GET)
//    프로필 이미지 선택을 위한 로직
    public ModelAndView getChoose(
            @RequestParam(value = "name", required = false) String name,
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user,
            HttpServletRequest request,
            ModelAndView modelAndView
    ) {
        if (user == null) {
            modelAndView.setViewName("redirect:/user/login");
        } else if (name != null) {
//            이름만 존재한다는 가정.
            ProfileEntity[] profiles = Arrays.stream(this.profileService.getProfiles(user))
                    .sorted(Comparator.comparing(ProfileEntity::getCreatedAt))
                    .toArray(ProfileEntity[]::new);
            ProfileEntity selectedProfile = null;
            for (ProfileEntity profile : profiles) {
                if (profile.getName().equals(name)) {
                    selectedProfile = profile;
                    break;
                }
            }
            if (selectedProfile == null) {
                modelAndView.addObject(ProfileEntity.ATTRIBUTE_NAME_PLURAL, profiles);
                modelAndView.setViewName("profile/choose");
            } else {
                request.getSession().setAttribute(ProfileEntity.ATTRIBUTE_NAME, selectedProfile);
                modelAndView.setViewName("redirect:/");
            }
        } else {
            ProfileEntity[] profiles = Arrays.stream(this.profileService.getProfiles(user))
                    .sorted(Comparator.comparing(ProfileEntity::getCreatedAt))
                    .toArray(ProfileEntity[]::new);
            modelAndView.addObject(ProfileEntity.ATTRIBUTE_NAME_PLURAL, profiles);
            modelAndView.setViewName("profile/choose");
        }
        return modelAndView;
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public String postModify (
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user,
            @RequestParam(value = "oldName") String oldName,
            ProfileEntity newProfile
    ) {
        Enum< ? extends IResult> result;
        if (user == null) {
            result = CommonResult.FAILURE;
        } else {
            newProfile.setUserEmail(user.getEmail());
            result = this.profileService.modifyProfile(oldName, newProfile);
        }
        JSONObject responseJson = new JSONObject();
        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
        return responseJson.toString();
    }
}
