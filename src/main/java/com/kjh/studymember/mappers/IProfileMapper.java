package com.kjh.studymember.mappers;

import com.kjh.studymember.entities.member.ProfileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface IProfileMapper {
    int insertProfile(ProfileEntity profile);

    ProfileEntity[] selectProfilesByEmail(@Param(value = "email") String email);

    int selectProfileCountByEmailAndName(@Param(value = "email") String email,
                                         @Param(value = "name") String name);
    int updateProfileByEmailAndName(@Param(value = "oldEmail") String oldEmail,
                                    @Param(value = "oldName") String oldEName,
                                    @Param(value = "newProfile") ProfileEntity newProfile
                                    );
}
