package com.pencilwith.apiserver.start.common.mapper;

import com.pencilwith.apiserver.start.common.model.dto.UserDto;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.start.common.model.request.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User requestToEntity(SignUpRequest request,
                                       PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(request.getId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .profileImage(request.getProfileImage())
                .genderType(request.getGenderType())
                .birth(request.getBirth())
                .locationType(request.getLocationType())
                .careerType(request.getCareerType())
                .introduction(request.getIntroduction())
                .build();
    }

    public static UserDto entityToDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .genderType(user.getGenderType())
                .birth(user.getBirth())
                .locationType(user.getLocationType())
                .careerType(user.getCareerType())
                .introduction(user.getIntroduction())
                .build();
    }
}
