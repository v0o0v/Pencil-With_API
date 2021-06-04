package com.pencilwith.apiserver.start.model.dto;

import com.pencilwith.apiserver.domain.entity.UserAgreement;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationResultDto {

    private boolean isRegistered;

    private String accessToken;

    private UserAgreement userAgreement;

    private String jwtToken;

    private String userId;

    public AuthenticationResultDto() {
    }

    @Builder
    public AuthenticationResultDto(boolean isRegistered, String accessToken, UserAgreement userAgreement, String jwtToken, String userId) {
        this.isRegistered = isRegistered;
        this.accessToken = accessToken;
        this.userAgreement = userAgreement;
        this.jwtToken = jwtToken;
        this.userId = userId;
    }
}
