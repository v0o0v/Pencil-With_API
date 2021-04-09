package com.pencilwith.apiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AuthControllerTest extends IntegrationTestSetup {

    private static final String AUTHORIZATION_CODE = "6AaSfq-DvnvivSuMdeUAh7KwZ2bLjs21Fnl-r9CjJGGJo5xsZftwKdrp8PzIs_f1nybkPgopb1QAAAF4sovQ1w";

    @Test
    @Order(1)
    @DisplayName("닉네임 중복 체크")
    void isNickNameDuplicated() throws Exception {
        mockMvc.perform(get("/api/auth/duplication/test1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @Order(3)
    @DisplayName("회원가입")
    void processSignUp() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("authorizationCode", AUTHORIZATION_CODE)
                .put("username", "username1")
                .put("profileImage", "profile 이미지 URI")
                .put("genderType", "FEMALE")
                .put("birth", "2000.01.01")
                .put("locationType", "INCHEON")
                .put("careerType", "INTERMEDIATE")
                .put("introduction", "자기소개입니다.");

        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    @DisplayName("카카오 인증")
    void processKakaoAuthentication() throws Exception {
        mockMvc.perform(post("/api/auth/kakao/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(AUTHORIZATION_CODE))
                .andExpect(status().isOk());
    }
}