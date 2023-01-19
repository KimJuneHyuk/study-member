package com.kjh.studymember.controllers;

import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.entities.member.PaymentContactVerificationCodeEntity;
import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.TelecomEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.services.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller(value = "com.kjh.studymember.controllers.PaymentController")
@RequestMapping(value = "/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView getAdd(
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user,
            ModelAndView modelAndView
    ) {
        if (user == null) {
            modelAndView.setViewName("redirect:/user/login");
        } else {
            modelAndView.addObject(TelecomEntity.ATTRIBUTE_NAME_PLURAL, this.paymentService.getTelecoms());
            modelAndView.setViewName("payment/add");
        }
        return modelAndView;
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView postAdd(
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user,
            ModelAndView modelAndView,
            PaymentEntity payment,
            PaymentContactVerificationCodeEntity contactVerificationCode

    )  {
        if (user == null) {
            modelAndView.setViewName("redirect:/user/login");
            return modelAndView;
        }

        IResult result = this.paymentService.addPayment(user,payment,contactVerificationCode);
        modelAndView.addObject(IResult.ATTRIBUTE_NAME, result);
        modelAndView.setViewName("payment/add");
        return modelAndView;
    }

    @RequestMapping(value = "required", method = RequestMethod.GET)
    public ModelAndView getRequired(
            @SessionAttribute(value = UserEntity.ATTRIBUTE_NAME, required = false) UserEntity user,
            ModelAndView modelAndView
    ) {
        if (user == null) {
            modelAndView.setViewName("redirect:/user/login");
        } else {
            modelAndView.setViewName("payment/required");
        }
        return modelAndView;
    }

//    문자 인증 처리 부분....
    @RequestMapping(value = "contact-verification-code", method = RequestMethod.GET)
    @ResponseBody
    public String getContactVerificationCode (
            PaymentContactVerificationCodeEntity paymentContactVerificationCode
    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        객체화 JSON Object
        JSONObject responseJson = new JSONObject();
        CommonResult result = this.paymentService.sendContactVerificationCode(paymentContactVerificationCode);
        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
        if (result == CommonResult.SUCCESS) {
            responseJson.put("salt", paymentContactVerificationCode.getSalt());
        }
        return responseJson.toString();
    }
//    public String getContactVerificationCode(
//            PaymentContactVerificationCodeEntity paymentContactVerificationCode
//    ) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        JSONObject responseJson = new JSONObject();
//        CommonResult result = this.paymentService.sendContactVerificationCode(paymentContactVerificationCode);
//
//        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
//        if (result == CommonResult.SUCCESS) {
//            responseJson.put("salt", paymentContactVerificationCode.getSalt());
//        }
//        return responseJson.toString();
//    }


    @RequestMapping(value = "contact-verification-code", method = RequestMethod.POST)
    @ResponseBody
    public String postContactVerificationCode(
            PaymentContactVerificationCodeEntity paymentContactVerificationCode
    ) {
        JSONObject responseJson = new JSONObject();
        IResult result = this.paymentService.checkContactVerificationCodeEntity(paymentContactVerificationCode);
        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
        return responseJson.toString();
    }












}
