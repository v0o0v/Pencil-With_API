package com.pencilwith.apiserver.start.common.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoTokenResponseDto {

    private String token_type;

    private String access_token;

    private String expires_in;

    private String refresh_token;

    private String refresh_token_expires_in;

    private String scope;

    public KakaoTokenResponseDto() {
    }

    @Builder
    public KakaoTokenResponseDto(String token_type, String access_token, String expires_in,
            String refresh_token, String refresh_token_expires_in, String scope) {
        this.token_type = token_type;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.refresh_token_expires_in = refresh_token_expires_in;
        this.scope = scope;
    }
}
