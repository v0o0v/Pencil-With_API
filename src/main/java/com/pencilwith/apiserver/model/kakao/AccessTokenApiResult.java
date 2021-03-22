package com.pencilwith.apiserver.model.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccessTokenApiResult {

    private String token_type;

    private String access_token;

    private Long expires_in;

    private String refresh_token;

    private Long refresh_token_expires_in;

    private String scope;

    public AccessTokenApiResult() {
    }

    @Builder
    public AccessTokenApiResult(String token_type, String access_token, Long expires_in,
            String refresh_token, Long refresh_token_expires_in, String scope) {
        this.token_type = token_type;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.refresh_token_expires_in = refresh_token_expires_in;
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "AccessTokenApiResult{" +
                "token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", refresh_token_expires_in=" + refresh_token_expires_in +
                ", scope='" + scope + '\'' +
                '}';
    }
}
