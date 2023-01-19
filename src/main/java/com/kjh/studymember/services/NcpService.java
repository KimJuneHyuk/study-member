package com.kjh.studymember.services;

import org.springframework.stereotype.Service;

@Service(value = "com.kjh.studymember.services.NcpService")
public class NcpService {
    private static final String NCP_ACCESS_KEY = "llFk3tl3DjAco5TFe9Nf";
    private static final String NCP_SECRET_KEY = "zeDujkQcxnKNtOtEuBq8vyoNTQYNvXGIGyko0Rkf";
    private static final String SERVICE_ID = "ncp:sms:kr:292567621184:study";
    private static final String CALLER_NUMBER = "01065659172"; // 발신자 번호.

    public void sendSms(
           String content,
           String... receiverNumbers
    ) {

    }
}
