package com.pencilwith.apiserver.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull
    @Size(min = 5, max = 20)
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    public LoginRequest() {
    }
}
