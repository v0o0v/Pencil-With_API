package com.pencilwith.apiserver.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationResultDto {

    private boolean isRegistered;

    private String authorizationCode;

    private String token;

    public AuthenticationResultDto() {
    }

    @Builder
    public AuthenticationResultDto(boolean isRegistered, String authorizationCode,
            String token) {
        this.isRegistered = isRegistered;
        this.authorizationCode = authorizationCode;
        this.token = token;
    }
}
