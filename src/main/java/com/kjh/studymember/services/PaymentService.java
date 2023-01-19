package com.kjh.studymember.services;

import com.kjh.studymember.IResult;
import com.kjh.studymember.StudyMemberApplication;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.emums.member.payment.ContactVerificationResult;
import com.kjh.studymember.entities.member.PaymentContactVerificationCodeEntity;
import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.TelecomEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.mappers.IPaymentMapper;
import com.kjh.studymember.utils.CryptoUtils;
import dev.hyukkk90.ncp.NcpResult;
import dev.hyukkk90.ncp.services.application.sens.sms.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service(value = "com.kjh.studymember.services.PaymentService")
public class PaymentService {
    private final IPaymentMapper paymentMapper;

    @Autowired
    public PaymentService(IPaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    @Transactional
    public CommonResult addPayment(UserEntity user,
                                   PaymentEntity payment,
                                   PaymentContactVerificationCodeEntity contactVerificationCode
    ) {
        contactVerificationCode = this.paymentMapper.selectPaymentContactVerificationCode(contactVerificationCode);
        if (!contactVerificationCode.isExpired()) {
            return CommonResult.FAILURE;
        }
        payment.setUserEmail(user.getEmail());
        if (this.paymentMapper.selectPaymentsByEmail(user.getEmail()).length == 0) {
            payment.setDefault(true);
        }
        return this.paymentMapper.insertPayment(payment) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }


    public PaymentEntity[] getPayments(UserEntity user) {
        return this.paymentMapper.selectPaymentsByEmail(user.getEmail());
    }

    public TelecomEntity[] getTelecoms() {
        return this.paymentMapper.selectTelecoms();
    }

    public IResult checkContactVerificationCodeEntity(PaymentContactVerificationCodeEntity contactVerificationCode) {
        PaymentContactVerificationCodeEntity p = this.paymentMapper.selectPaymentContactVerificationCode(contactVerificationCode);
        if (p == null) {
            return CommonResult.FAILURE;
        } else if (p.getExpiresAt().getTime() - (new Date()).getTime() < 0L) {
            return ContactVerificationResult.FAILURE_EXPIRED;
        }
        p.setExpired(true);
        this.paymentMapper.updatePaymentContactVerificationCode(p);
        return CommonResult.SUCCESS;
    }

    public CommonResult sendContactVerificationCode(PaymentContactVerificationCodeEntity contactVerificationCode)
            throws IOException,
            NoSuchAlgorithmException,
            InvalidKeyException {
//        인증 번호 6자리 만들어주는 code 생성.
        String code = RandomStringUtils.randomNumeric(6);
//        인증 번호 보내면서 받은 문자에 대한 해싱 salt 해주는곳.
        String salt = CryptoUtils.hash(CryptoUtils.Hash.SHA_512, String.format("%s%s%s%d%s%f",
                contactVerificationCode.getContactFirst(),
                contactVerificationCode.getContactSecond(),
                contactVerificationCode.getContactThird(),
                contactVerificationCode.getCreatedAt().getTime(),
                code,
                Math.random()));
//        인증번호 유효시간 5분
        Date expiresAt = DateUtils.addMinutes(contactVerificationCode.getCreatedAt(), 5);
        contactVerificationCode.setCode(code)
                .setSalt(salt)
                .setExpiresAt(expiresAt);
        if (this.paymentMapper.insertPaymentContactVerificationCode(contactVerificationCode) == 0) {
//            추가를 못한경우,,추가 실패.
            return CommonResult.FAILURE;
        }
//        문자 전송 구간 쿼리.....준비
        String content = String.format("[스터디멤버 TEST 넷플릭스] 결제 수단 추가 인증번호 [%s] 를 입력해주세요.",
                contactVerificationCode.getCode());
//        여기서 getCode 로 인증번호 6자리 보내주고.
        SmsMessage message = SmsMessage.build().setContact(new SmsContact(String.format("%s%s%s",
                contactVerificationCode.getContactFirst(),
                contactVerificationCode.getContactSecond(),
                contactVerificationCode.getContactThird())));
//      휴대전화번호 가져와서 등록하고.
        SmsMessagesArgs args = SmsMessagesArgs.build()
                .setContentType(SmsContentType.COMM)
                .setCaller(StudyMemberApplication.NCP_CALLER)
                .setGlobalContent(content)
                .addMessages(message);
        SmsMessagesApiProvider apiProvider = new SmsMessagesApiProvider(StudyMemberApplication.NCP_AUTH);
        NcpResult<SmsMessagesResult> sendResult = apiProvider.send(args);
        return sendResult.result == SmsMessagesResult.SUCCESS
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }
}























