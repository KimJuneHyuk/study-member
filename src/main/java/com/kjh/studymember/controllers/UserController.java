package com.kjh.studymember.controllers;

import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.entities.member.EmailVerificationKeyEntity;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.services.ProfileService;
import com.kjh.studymember.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller(value = "com.kjh.studymember.controllers.UserController")
@RequestMapping(value = "/user")
public class UserController {
    private final ProfileService profileService;
    private final UserService userService;

    public UserController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }
    /****************************ogin 처리 부분*************************/
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView getLogin (
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("user/login");
        return modelAndView;
    }


    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ModelAndView postLogin(
            HttpServletRequest request,
            ModelAndView modelAndView,
            UserEntity user ) {
        Enum< ? extends IResult> result = this.userService.login(user);
        if (result == CommonResult.SUCCESS) {
            request.getSession().setAttribute(UserEntity.ATTRIBUTE_NAME, user);
        }
        modelAndView.addObject(IResult.ATTRIBUTE_NAME, result);
        modelAndView.setViewName("user/login");
        return modelAndView;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView getLogout(ModelAndView modelAndView,
                                  HttpSession session) {
        session.removeAttribute(UserEntity.ATTRIBUTE_NAME);
        session.removeAttribute(ProfileEntity.ATTRIBUTE_NAME);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


    //    주소찾기와 이메일 인증 회원가입 페이지
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public ModelAndView getRegister(
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("user/register");
        return modelAndView;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ModelAndView postRegister(
            ModelAndView modelAndView,
            UserEntity user
    ) throws MessagingException {

        Enum <? extends IResult> result = this.userService.register(user);
//        user.setCreatedAt(currentDate);
//        if (this.userService.getUserCountByEmail(user.getEmail())> 0) {
//            result = RegisterResult.FAILURE_DUPLICATE_EMAIL;
////            이미 회원가입된 이메일이기떄문에 중복으로 인한 회원가입 실패 반환
//        } else if (this.userService.register(user) == 2) {
//            result = CommonResult.SUCCESS;
////            비밀번호, 인증키 단방향 암호화 통과로 인한 affectedRecords 가 2이기 떄문에 회원가입 성공 이메일 인증하로 갈것,
//        } else {
//            result = CommonResult.FAILURE;
////            알 수 없는 이유로 회원가입 실패를 반환한다.
//        }
//        System.out.println(result.name());
        if (result == CommonResult.SUCCESS) {
            Date currentDate = new Date();
            ProfileEntity profile = ProfileEntity.build()
                    .setUserEmail(user.getEmail())
                    .setName(user.getName())
                    .setCreatedAt(currentDate)
                    .setLastAccessAt(currentDate)
                    .setProfileIndex( (int) (Math.random() * 12 + 1) );
            //        math.rendom() : 0.0 ~ 0.999999999999999
            this.profileService.addProfile(profile);
        }

        modelAndView.addObject(IResult.ATTRIBUTE_NAME, result);
//      mav 를 통해 html 에서 "result" 이름으로 결과를 받을 수 있ㄱㅔ 만들어 주었다.
        modelAndView.setViewName("user/register");
//        영향받는 html 의 위치를 mav.setViewName("user/register") 표시해주었고, 반환 한다.
        return modelAndView;
    }


    @RequestMapping(value = "verify-email", method = RequestMethod.GET)
    public ModelAndView getVerifyEmail(ModelAndView modelAndView,
                                       EmailVerificationKeyEntity emailVerificationKey,
                                       UserEntity user) {
        Enum <? extends IResult> result = this.userService.verifyEmail(emailVerificationKey);

        modelAndView.addObject(IResult.ATTRIBUTE_NAME, result);
        modelAndView.setViewName("user/verify-email");
        return modelAndView;
    }



}
