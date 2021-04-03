package com.pencilwith.apiserver.model.dto;

import lombok.Getter;

@Getter
public class JwtTokenDto {

    private String token;

    public JwtTokenDto() {
    }

    public JwtTokenDto(String token) {
        this.token = token;
    }
}
