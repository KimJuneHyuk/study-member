package com.kjh.studymember.mappers;

import com.kjh.studymember.entities.member.PaymentContactVerificationCodeEntity;
import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.TelecomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface IPaymentMapper {
    int insertPayment(PaymentEntity payment);
    int insertPaymentContactVerificationCode(PaymentContactVerificationCodeEntity p);
    PaymentContactVerificationCodeEntity selectPaymentContactVerificationCode(PaymentContactVerificationCodeEntity p);
    PaymentEntity[] selectPaymentsByEmail(@Param(value = "email") String email);
    TelecomEntity[] selectTelecoms();
    int updatePaymentContactVerificationCode(PaymentContactVerificationCodeEntity p);
}
