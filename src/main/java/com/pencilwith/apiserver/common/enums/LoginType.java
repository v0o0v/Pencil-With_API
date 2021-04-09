package com.pencilwith.apiserver.common.enums;

import lombok.Getter;

@Getter
public enum LoginType {
    KAKAO("kakao"), GOOGLE("google");

    private String type;

    LoginType(String type) {
        this.type = type;
    }
}
