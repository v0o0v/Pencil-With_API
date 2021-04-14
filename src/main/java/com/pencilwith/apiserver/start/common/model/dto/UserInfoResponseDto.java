package com.pencilwith.apiserver.start.common.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponseDto {

    private String loginType;

    @JsonProperty("id")
    @JsonAlias("sub")
    private String userId;

    @JsonAlias("picture")
    private String profileImage;

    public UserInfoResponseDto() {
    }
}
