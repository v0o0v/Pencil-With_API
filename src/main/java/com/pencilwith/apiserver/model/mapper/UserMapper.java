package com.pencilwith.apiserver.model.mapper;

import com.pencilwith.apiserver.model.entity.Authority;
import com.pencilwith.apiserver.model.entity.User;
import com.pencilwith.apiserver.model.request.SignUpRequest;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User requestToEntity(SignUpRequest request,
                                       Set<Authority> authorities,
                                       PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickName(request.getNickName())
                .profileImage(request.getProfileImage())
                .gender(request.getGender())
                .birth(request.getBirth())
                .location(request.getLocation())
                .career(request.getCareer())
                .introduction(request.getIntroduction())
                .authorities(authorities)
                .build();
    }
}
