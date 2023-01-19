package com.kjh.studymember;


import dev.hyukkk90.ncp.NcpAuth;
import dev.hyukkk90.ncp.NcpResult;
import dev.hyukkk90.ncp.services.application.sens.sms.*;
import org.junit.Assert;
import org.junit.Test;

public class SignatureTest {
    @Test
    public void test() throws Exception {
        NcpAuth auth = new NcpAuth(
                "llFk3tl3DjAco5TFe9Nf",
                "zeDujkQcxnKNtOtEuBq8vyoNTQYNvXGIGyko0Rkf",
                "ncp:sms:kr:292567621184:study"
        );
        SmsContact caller = new SmsContact("01065659172");
        SmsMessagesArgs messagesArgs = SmsMessagesArgs.build()
                .setContentType(SmsContentType.COMM)
                .setCaller(caller)
                .setGlobalContent("문자v인증 발송 테스트!!")
                .addMessages(SmsMessage.build().setContact(caller));
        SmsMessagesApiProvider messagesApiProvider = new SmsMessagesApiProvider(auth);
        NcpResult<SmsMessagesResult> sendResult = messagesApiProvider.send(messagesArgs);
        System.out.println("Result : " + sendResult.result);
        System.out.println("Status Code : " + sendResult.statusCode);
        Assert.assertEquals(sendResult.statusCode, 202);
    }
//        https://api.ncloud-docs.com/docs/common-ncpapi

//    @Test
//    public void test() throws Exception {
//        NcpAuth auth = new NcpAuth(
//                "llFk3tl3DjAco5TFe9Nf",
//                "zeDujkQcxnKNtOtEuBq8vyoNTQYNvXGIGyko0Rkf",
//                "ncp:sms:kr:292567621184:study"
//        );
//        SmsContact caller = new SmsContact("01065659172");
////        SmsContact susin = new SmsContact("01091367750");
//        SmsMessage message = SmsMessage.build()
//                .setContact(caller);
//        SmsMessagesArgs messagesArgs = SmsMessagesArgs.build()
//                .setContentType(SmsContentType.COMM)
//                .setCaller(caller)
//                .setGlobalContent("문자 인증 발송 테스트 내용입니다.")
//                .addMessages(SmsMessage.build().setContact(caller));
//        SmsMessagesApiProvider messagesApiProvider = new SmsMessagesApiProvider(auth);
//        NcpResult<SmsMessagesResult> sendResult = messagesApiProvider.send(messagesArgs);
//
//        System.out.println("전송 결과 : " + sendResult.result);
//        System.out.println("응답 코드 : " + sendResult.statusCode);
//        final String ncpHostName = "https://sens.apigw.ntruss.com";
//        final String ncpAccessKey = "llFk3tl3DjAco5TFe9Nf";
//        final String ncpSecretKey = "zeDujkQcxnKNtOtEuBq8vyoNTQYNvXGIGyko0Rkf";
//        final String ncpServicesId = "ncp:sms:kr:292567621184:study";
//        final String ncpRequestUrlType = "/messages";
//        final String ncpRequestUrl = String.format("/sms/v2/services/%s%s", ncpServicesId, ncpRequestUrlType);
//        final String ncpTimestamp = Long.toString(System.currentTimeMillis());
//        System.out.println("=== NCLOUD SMS 전송 테스트 ===");
//
//        System.out.println("    - NCP API 시그니처 생성 시작");
//        String ncpSignature = String.format("POST %s\n%s\n%s", ncpRequestUrl, ncpTimestamp, ncpAccessKey);
//
////        String method = "POST";
////        String url = "/sns/v2/services/ncp:sms:kr:292567621184:study/messages";
////        String timestamp = new Date().toString();
////
////        String message = new StringBuilder()
////                .append(method)
////                .append(" ")
////                .append(url)
////                .append("\n")
////                .append(timestamp)
////                .append("\n")
////                .append(ncpAccessKey)
////                .toString();
//        SecretKeySpec secretKeySpec = new SecretKeySpec(ncpSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(secretKeySpec);
//        byte[] rawHmac = mac.doFinal(ncpSignature.getBytes(StandardCharsets.UTF_8));
//        ncpSignature = Base64.encodeBase64String(rawHmac);
//        System.out.println("    - NCP API 시그니처 : " + ncpSignature);
//        System.out.println("    - NCP API 시그니처 생성 완료");
//        System.out.println();
//
//
//        System.out.println("    - SMS JSON 생성 시작 ");
//        JSONObject bodyJson = new JSONObject();
//        JSONArray toArray = new JSONArray();
//        JSONObject toJson = new JSONObject();
//
//        toJson.put("to", "01065659172");
//        toArray.put(toJson);
//        bodyJson.put("type", "SMS");
//        bodyJson.put("contentType", "COMM");
//        bodyJson.put("countryCode", "82");
//        bodyJson.put("from", "01065659172");
//        bodyJson.put("content", "문자 API 발송 테스트!!\n문자를 받으실 경우 테스트 성공되었음을 알립니다.");
//        bodyJson.put("messages", toArray);
//        System.out.println("    - SMS JSON 생성 완료 : " + bodyJson);
//        System.out.println();
//
//
//        System.out.println("    - SMS 발송 요청 전송");
//        URL url = new URL(String.format("%s%s", ncpHostName, ncpRequestUrl));
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//        connection.setUseCaches(false);
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setRequestProperty("x-ncp-apigw-timestamp", ncpTimestamp);
//        connection.setRequestProperty("x-ncp-iam-access-key", ncpAccessKey);
//        connection.setRequestProperty("x-ncp-apigw-signature-v2", ncpSignature);
//        connection.setRequestMethod("POST");
//
//        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        outputStream.write(bodyJson.toString().getBytes());
//        outputStream.flush();
//        outputStream.close();
//
//        System.out.println("    - 응답 코드 : " + connection.getResponseCode());
//        BufferedReader reader;
//        if (connection.getResponseCode() == 202) {
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        } else {
//            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//        }
//        String responseLine;
//        StringBuilder responseBuilder = new StringBuilder();
//        while ((responseLine = reader.readLine()) != null) {
//            responseBuilder.append(responseLine);
//        }
//        String response = responseBuilder.toString();
//        reader.close();
//        System.out.println("    -응답 내용 : " + response);
//        System.out.println("=== NCLOUD SMS 전송 테스트 종료 ===");
//


}

