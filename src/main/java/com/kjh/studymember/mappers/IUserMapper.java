package com.kjh.studymember.mappers;

import com.kjh.studymember.entities.member.EmailVerificationKeyEntity;
import com.kjh.studymember.entities.member.PaymentEntity;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserMapper {
    int insertProfile(ProfileEntity profile);
    int insertUser(UserEntity user);
//    유저 가입을 위해 register 페이지를 통해 입력된 값을 저장하기 고 기존유저인지 비교하기 위한 쿼리 입니다.
    int insertEmailVerificationKey(EmailVerificationKeyEntity emailVerificationKey);
//    인증키를 sha-512로 해싱하여 key column 에 저장하여 보내준다. key 끼리 비교하여 동일하다면 인증 성공을 반환하기 위해 기록을 남긴다.

    UserEntity selectUserByEmailAndPassword(@Param(value = "email") String email,
                                            @Param(value = "password") String password);
    int selectUserCountByEmail(@Param(value = "email")String email);
//    electUserCountByEmail 은 가입된 이메일이 있는지 중복 확인을 위해 email 존재를 int 로 확인한다.
//    여기서 어노테이션@Param은 userMapper.xml에 DBMS 로직 의 where = 이름과 동일 해야한다.
    int updateUser(UserEntity user);
    int updateEmailVerificationKey(EmailVerificationKeyEntity emailVerificationKey);
    UserEntity selectUserByEmail(@Param(value = "email") String email);
    EmailVerificationKeyEntity selectEmailVerificationKeyByKey(@Param(value = "key") String key);


}
