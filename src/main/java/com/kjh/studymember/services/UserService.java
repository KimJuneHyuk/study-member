package com.kjh.studymember.services;

import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.emums.member.user.LoginResult;
import com.kjh.studymember.emums.member.user.RegisterResult;
import com.kjh.studymember.emums.member.user.VerifyEmailResult;
import com.kjh.studymember.entities.member.EmailVerificationKeyEntity;
import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.mappers.IUserMapper;
import com.kjh.studymember.utils.CryptoUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service(value = "com.kjh.studymember.services.UserService")
public class UserService  {
    public static final int REGISTER_EMAIL_VERIFICATION_DUE_MINUTES = 60;
    private final IUserMapper userMapper;
    private final JavaMailSender javaMailSender;
//    의존성 주입으로 인해 JavaMailSender javaMailSender 사용이 가능해졌다
//    org.springframework.mail.javamail
    private final SpringTemplateEngine springTemplateEngine;
//    의존성 주입으로 인해 SpringTemplateEngine springTemplateEngine 사용이 가능해 졌다.
//    org.springframework.mail.javamail
    @Autowired
    public UserService(IUserMapper userMapper, JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.userMapper = userMapper;
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }


    public Enum<? extends IResult> login(UserEntity user) {
//        Login 을 위한 데이터 로직
        user.setPassword(CryptoUtils.hash(CryptoUtils.Hash.SHA_512, user.getPassword()));
        UserEntity signedUser = this.userMapper.selectUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (signedUser == null) {
            return CommonResult.FAILURE;
        }
        if (!signedUser.isEmailVerified()) {
            return LoginResult.FAILURE_EMAIL_NOT_VERIFIED;
        }
        user.setEmail(signedUser.getEmail())
                .setPassword(signedUser.getPassword())
                .setName(signedUser.getName())
                .setCreatedAt(signedUser.getCreatedAt())
                .setEmailVerified(signedUser.isEmailVerified());

        return CommonResult.SUCCESS;
    }

//    public int getUserCountByEmail(String email) {
////        중복유저 존재 여부 확인을 위한 로직
//        return this.userMapper.selectUserCountByEmail(email);
//    }

    @Transactional
    public Enum<? extends IResult> register(UserEntity user)
            throws MessagingException {
//        회원가입 로직
//        회원 가입을 위해 유저의 기록을 남기기 위한 로직
//        password 를 단방향 암호화하기 위한 로직
        if (this.userMapper.selectUserCountByEmail(user.getEmail()) > 0) {
            return RegisterResult.FAILURE_DUPLICATE_EMAIL;
        }
        Date currentDate = new Date();
        user.setPassword(CryptoUtils.hash(CryptoUtils.Hash.SHA_512, user.getPassword()));
        user.setCreatedAt(currentDate);
        if (this.userMapper.insertUser(user) == 0) {
            return CommonResult.FAILURE;
        }




//        int affectedRecords = 0;
//        String password = user.getPassword(); // test1234를 받아왔따
//        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512"); //SHA-512 사용하여 비밀번호 단방향암호화
//        messageDigest.reset(); // messageDigest 초기화 시켜준다.
//        messageDigest.update(password.getBytes(StandardCharsets.UTF_8)); //password 문법을 UTF-8로 설정한다.
//        password = String.format("%0128x", new BigInteger(1,messageDigest.digest())); //16진법 128자로 password 를 난수화 하였다.
//        user.setPassword(password); //그리고 난수화된 password 를 다시 password 로 넣어준다.
//        affectedRecords += this.userMapper.insertUser(user);
////      (영향받은기록) affectedRecords = 0 에서 + 1이된다.
////        위의 로직을 모두 통과 했을경우 db에 하나의 회원정보가 추가 되기 때문이다
//        final int emailDueMinutes = 60;
//        emailDueMinutes(이메일 인증 마감 시간) = 60분.
//        currentDate(현재 시간)
        String key = CryptoUtils.hash(CryptoUtils.Hash.SHA_512, String.format("%s%s%s%f%f",
                user.getEmail(),
                user.getPassword(),
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate),
                Math.random(),
                Math.random()));
//        key = CryptoUtils.hash(CryptoUtils.Hash.SHA_512, key);
//        단반향 암호화를 key 가 시켜준다.
//        user 의 레코드의 모든 정보가 그대로 남아있다면 보안 취약점이 생기기 떄문에 모든 정보를 암호화 해주어 보안을 강화했다.
//        messageDigest.reset(); //초기화 시켜준다
//        messageDigest.update(key.getBytes(StandardCharsets.UTF_8));
//        key = String.format("%0128x", new BigInteger(1,messageDigest.digest()));
        EmailVerificationKeyEntity emailVerificationKey = EmailVerificationKeyEntity.build()
                .setKey(key)
//                여기서 email,password(hsa-512), 가입일시분초 해싱된 모든부분이 key 로 들어간다.
                .setUserEmail(user.getEmail())
                .setCreatedAt(currentDate)
                .setExpiresAt(DateUtils.addMinutes(currentDate, UserService.REGISTER_EMAIL_VERIFICATION_DUE_MINUTES))
//                인증 만료 시간을 비교한다. addMinutes(현재 시간, + 60분 뒤)
                .setExpired(false);
        if (this.userMapper.insertEmailVerificationKey(emailVerificationKey) == 0) {
            return CommonResult.FAILURE;
        }

        Context context = new Context();
//        context(문맥)
        context.setVariable("email",user.getEmail());
        context.setVariable("name",user.getName());
        context.setVariable("key",emailVerificationKey.getKey());

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("넷플릭스 화원가입 인증 메일/ 제한시간 60분.");
        mimeMessageHelper.setFrom("kjh65659172@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(this.springTemplateEngine.process("user/email-verification-email", context) , true);
        this.javaMailSender.send(mimeMessage);

        return CommonResult.SUCCESS;
    }

    @Transactional
//    아래 조건중에 하나라도 실패할 경우 전체 작동 전 상황으로 되돌린다. 라는 어노테이션
//    이메일이 인증 되었는지 안되었는지 되었다면 DBMS 에 데이터로써 수정을 하기 위해 남기위한 로직이다.
    public Enum < ? extends IResult> verifyEmail(EmailVerificationKeyEntity emailVerificationKey) {
        emailVerificationKey = this.userMapper.selectEmailVerificationKeyByKey(emailVerificationKey.getKey());
        if (emailVerificationKey == null) {
            return CommonResult.FAILURE;
        }
//        dt1.compareTo(dt2) > 0 : dt1가 dt2 보다 미래다.
//        dt1.compareTo(dt2) < 0 : dt1가 dt2 보다 과거다.
        if (emailVerificationKey.isExpired() || new Date().compareTo(emailVerificationKey.getExpiresAt()) > 0) {
            return VerifyEmailResult.FAILURE_EXPIRED;
        }
        emailVerificationKey.setExpired(true);
        if (this.userMapper.updateEmailVerificationKey(emailVerificationKey) == 0) {
            return CommonResult.FAILURE;
        }
        UserEntity user = this.userMapper.selectUserByEmail(emailVerificationKey.getUserEmail());
        user.setEmailVerified(true);
        if (this.userMapper.updateUser(user) == 0) {
            return CommonResult.FAILURE;
        }
        return CommonResult.SUCCESS;
    }

}
