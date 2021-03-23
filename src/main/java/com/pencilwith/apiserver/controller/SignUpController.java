package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.kakao.AccessTokenApiResult;
import com.pencilwith.apiserver.model.kakao.KakaoUserInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/signUp")
public class SignUpController {

    @GetMapping("/kakao")
    public void signUpByKakao(@RequestParam(name = "code") String authorizationCode) {
        String accessToken = getAccessToken(authorizationCode);
        KakaoUserInfo kakaoUserInfo = getKakaoUserInfo(accessToken);
        System.out.println(kakaoUserInfo);
    }

    private String getAccessToken(String authorizationCode) {
        AccessTokenApiResult block = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", "7c5bbd138597c14273ea1d9267c89ae5")
                        .with("redirect_uri", "http://www.depther.me:8080/signUp/kakao")
                        .with("code", authorizationCode))
                .retrieve()
                .bodyToMono(AccessTokenApiResult.class)
                .block();
        return block.getAccess_token();
    }

    public KakaoUserInfo getKakaoUserInfo(String accessToken) {
        return WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class).block();
    }

    @PostMapping("/google")
    public void signUpByGoogle(HttpServletRequest request, String code) {
        System.out.println("aaa");
    }
}
