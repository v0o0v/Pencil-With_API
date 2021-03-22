package com.pencilwith.apiserver.model.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KakaoUserInfo {

    private String id;

    private String kakao_account;

    private String properties;

    public KakaoUserInfo() {
    }

    @Builder
    public KakaoUserInfo(String id, String kakao_account, String properties) {
        this.id = id;
        this.kakao_account = kakao_account;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "KakaoUserInfo{" +
                "id='" + id + '\'' +
                ", kakao_account='" + kakao_account + '\'' +
                ", properties='" + properties + '\'' +
                '}';
    }
}
