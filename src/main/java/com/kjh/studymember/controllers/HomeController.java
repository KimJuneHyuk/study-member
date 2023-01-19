package com.kjh.studymember.controllers;

import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.services.PaymentService;
import com.kjh.studymember.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "com.kjh.studymember.controllers.HomeController")
@RequestMapping(value = "/")
public class HomeController {

    private final ProfileService profileService;
    private final PaymentService paymentService;

    @Autowired
    public HomeController( ProfileService profileService, PaymentService paymentService) {
        this.profileService = profileService;
        this.paymentService = paymentService;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getIndex(
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false)UserEntity user,
            @SessionAttribute(value = ProfileEntity.ATTRIBUTE_NAME, required = false)ProfileEntity profile,
            ModelAndView modelAndView
    ) {
        if (user == null) {
            modelAndView.setViewName("home/index.unsigned");
        } else {
//            결제 정보
            PaymentEntity[] payments = this.paymentService.getPayments(user);
            if (payments.length == 0) {
                modelAndView.setViewName("redirect:/payment/required");
            } else if (profile == null) {
                modelAndView.setViewName("redirect:/profile/choose");
            } else {
                modelAndView.addObject(ProfileEntity.ATTRIBUTE_NAME_PLURAL, this.profileService.getProfiles(user));
                modelAndView.setViewName("home/index.signed");
            }
        }

        return modelAndView;
    }
}
