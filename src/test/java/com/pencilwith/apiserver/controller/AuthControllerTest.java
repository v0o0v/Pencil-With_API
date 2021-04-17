package com.pencilwith.apiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@DatabaseSetup(value = {
        "/database/auth_controller_test.xml"
}, type = DatabaseOperation.CLEAN_INSERT)
class AuthControllerTest extends IntegrationTestSetup {

    // TODO: 통합 테스트 실행 시 ACCESS_TOKEN 자동으로 얻어오도록 개선
    private static final String ACCESS_TOKEN = "ya29.a0AfH6SMCs1S1hnTYozJ2yRgXvAj52JVQhp_XKi9Un9ApZrNiQdgfUoabUIpFCTQaiP0p36y2X30hhbWXk2b2wvUlSHGy-veLgt4fDkngfXQU74solK49OnFIJwvvLHrGzlWJv91sH5G8Oc-L7CDNW1nC2ydkTmQ";

    @Test
    @DisplayName("닉네임 중복 체크")
    void isNickNameDuplicated() throws Exception {
        mockMvc.perform(get("/api/auth/duplication/test2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body").value(false));
    }

    @Test
    @DisplayName("카카오 회원가입")
    void processKakaoAuthentication_processSignUp() throws Exception {
        // 카카오 회원 인증 요청
        mockMvc.perform(post("/api/auth/kakao/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk());

        // 회원가입 요청 파라미터
        JSONObject jsonObject = new JSONObject()
                .put("accessToken", ACCESS_TOKEN)
                .put("username", "username1")
                .put("profileImage", "profile 이미지 URI")
                .put("genderType", "FEMALE")
                .put("birth", "2000.01.01")
                .put("locationType", "INCHEON")
                .put("careerType", "INTERMEDIATE")
                .put("introduction", "자기소개입니다.");

        // 회원가입 요청
        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("구글 회원가입")
    void processGoogleAuthentication_processSignUp() throws Exception {
        // 구글 회원 인증 요청
        mockMvc.perform(post("/api/auth/google/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk());

        // 회원가입 요청 파라미터
        JSONObject jsonObject = new JSONObject()
                .put("accessToken", ACCESS_TOKEN)
                .put("username", "username1")
                .put("profileImage", "profile 이미지 URI")
                .put("genderType", "FEMALE")
                .put("birth", "2000.01.01")
                .put("locationType", "INCHEON")
                .put("careerType", "INTERMEDIATE")
                .put("introduction", "자기소개입니다.");

        // 회원가입 요청
        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }
}