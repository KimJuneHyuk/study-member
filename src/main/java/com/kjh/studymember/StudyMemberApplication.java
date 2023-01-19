package com.kjh.studymember;

import dev.hyukkk90.ncp.NcpAuth;
import dev.hyukkk90.ncp.services.application.sens.sms.SmsContact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyMemberApplication {
//    의존성 추가부분 수정.
    public static final NcpAuth NCP_AUTH = new NcpAuth(
            "llFk3tl3DjAco5TFe9Nf",
            "zeDujkQcxnKNtOtEuBq8vyoNTQYNvXGIGyko0Rkf",
            "ncp:sms:kr:292567621184:study"
    );
    public static final SmsContact NCP_CALLER = new SmsContact("01065659172");


    public static void main(String[] args) {
        SpringApplication.run(StudyMemberApplication.class, args);
    }

}
