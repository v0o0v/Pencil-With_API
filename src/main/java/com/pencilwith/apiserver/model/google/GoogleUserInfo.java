package com.pencilwith.apiserver.model.google;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleUserInfo {

    private String id;

    private String name;

    private String imageUrl;

    private String email;

    public GoogleUserInfo() {
    }

    @Builder
    public GoogleUserInfo(String id, String name, String imageUrl, String email) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
    }
}
