package com.pencilwith.apiserver.model.mapper;

import com.pencilwith.apiserver.model.entity.Account;
import com.pencilwith.apiserver.model.request.SignUpRequest;

public class AccountMapper {

    public static Account requestToEntity(SignUpRequest request) {
        return Account.builder()
                .nickName(request.getNickName())
                .profileImage(request.getProfileImage())
                .gender(request.getGender())
                .birth(request.getBirth())
                .location(request.getLocation())
                .career(request.getCareer())
                .introduction(request.getIntroduction())
                .build();
    }
}
