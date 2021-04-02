package com.pencilwith.apiserver.model.mapper;

import com.pencilwith.apiserver.model.entity.User;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User requestToEntity(SignUpRequest request,
                                       PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .profileImage(request.getProfileImage())
                .genderType(request.getGenderType())
                .birth(request.getBirth())
                .locationType(request.getLocationType())
                .careerType(request.getCareerType())
                .introduction(request.getIntroduction())
                .build();
    }
}
