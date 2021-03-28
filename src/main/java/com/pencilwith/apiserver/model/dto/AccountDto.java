package com.pencilwith.apiserver.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {

    private String nickName;

    public AccountDto() {
    }

    @Builder
    public AccountDto(String nickName) {
        this.nickName = nickName;
    }
}
