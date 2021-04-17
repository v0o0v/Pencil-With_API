package com.pencilwith.apiserver.start.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationResultDto {

    private boolean isRegistered;

    private String accessToken;

    private String token;

    public AuthenticationResultDto() {
    }

    @Builder
    public AuthenticationResultDto(boolean isRegistered, String accessToken,
            String token) {
        this.isRegistered = isRegistered;
        this.accessToken = accessToken;
        this.token = token;
    }
}
