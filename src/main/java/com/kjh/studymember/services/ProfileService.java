package com.kjh.studymember.services;

import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.emums.member.profile.AddResult;
import com.kjh.studymember.emums.member.profile.ModifyResult;
import com.kjh.studymember.entities.member.ProfileEntity;
import com.kjh.studymember.entities.member.UserEntity;
import com.kjh.studymember.mappers.IProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "com.kjh.studymember.services.ProfileService")
public class ProfileService {
    private final IProfileMapper profileMapper;
    @Autowired
    public ProfileService(IProfileMapper profileMapper) {
        this.profileMapper = profileMapper;
    }

    @Transactional
    public Enum< ? extends IResult> addProfile (ProfileEntity profile) {
        if (this.profileMapper.selectProfileCountByEmailAndName(profile.getUserEmail(), profile.getName()) > 0) {
//           만약에 새로 추가 하려고 하는데 이메일이랑 이름이 중복일 경우 실패
            return AddResult.FAILURE_DUPLICATE_NAME;
        }
//        중복이 없을 경우 InsertProfile 의 ProfileEntity 를 통해 추가한다.
        return this.profileMapper.insertProfile(profile) > 0
                ? CommonResult.SUCCESS
                : CommonResult.FAILURE;
    }

    public ProfileEntity[] getProfiles(UserEntity user) {
        return this.profileMapper.selectProfilesByEmail(user.getEmail());
    }

    public Enum<? extends IResult> modifyProfile ( String oldName , ProfileEntity newProfile) {
        if (!oldName.equals(newProfile.getName()) && this.profileMapper.selectProfileCountByEmailAndName(newProfile.getUserEmail(), newProfile.getName()) > 0) {
            return ModifyResult.FAILURE_DUPLICATE_NAME;
        }
        return this.profileMapper.updateProfileByEmailAndName( newProfile.getUserEmail(),oldName, newProfile) > 0 ? CommonResult.SUCCESS : CommonResult.FAILURE;
    }
}
