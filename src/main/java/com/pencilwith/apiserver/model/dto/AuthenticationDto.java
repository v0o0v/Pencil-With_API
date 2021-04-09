package com.pencilwith.apiserver.model.dto;

import lombok.Getter;

@Getter
public class AuthenticationDto {

    private String userId;

    private String password;

    public AuthenticationDto() {
    }

    public AuthenticationDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
