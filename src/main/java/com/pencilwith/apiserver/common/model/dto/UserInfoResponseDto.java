package com.pencilwith.apiserver.common.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {

    private String loginType;

    @JsonProperty("id")
    private String userId;

    private String profileImage;

    public UserInfoResponseDto() {
    }
}
