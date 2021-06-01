package com.pencilwith.apiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;

@DatabaseSetup(value = {
        "/database/auth_controller_test.xml"
}, type = DatabaseOperation.CLEAN_INSERT)
class AuthControllerTest extends IntegrationTestSetup {

    // TODO: 통합 테스트 실행 시 ACCESS_TOKEN 자동으로 얻어오도록 개선
    private static final String ACCESS_TOKEN = "XgNGp1zSVjmSYweJhGUIdgE16tcElq1xLV2hcwo9dZwAAAF44BCLsg";

    @Test
    @Order(1)
    @DisplayName("닉네임 중복 체크")
    void isNickNameDuplicated() throws Exception {
        mockMvc.perform(get("/api/auth/duplication/test2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body").value(false));
    }

    @Test
    @Order(2)
    @DisplayName("카카오 회원가입 및 로그인")
    @Disabled
    void processKakaoAuthentication_processSignUp_processKakaoAuthentication() throws Exception {
        // 카카오 회원 인증 요청
        mockMvc.perform(post("/api/auth/kakao/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(false))
                .andExpect(jsonPath("$.body.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.body.jwtToken").doesNotExist())
                .andExpect(jsonPath("$.body.userAgreement").exists())
                .andExpect(jsonPath("$.body.userAgreement.id").value(2));

        // 회원가입 요청 파라미터
        JSONObject jsonObject = new JSONObject()
                .put("accessToken", ACCESS_TOKEN)
                .put("username", "username2")
                .put("profileImage", "profile 이미지 URI")
                .put("genderType", "FEMALE")
                .put("birth", "2000.01.01")
                .put("locationType", "인천")
                .put("careerType", "INTERMEDIATE")
                .put("introduction", "자기소개입니다.");

        // 회원가입 요청
        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(true))
                .andExpect(jsonPath("$.body.accessToken").doesNotExist())
                .andExpect(jsonPath("$.body.jwtToken").exists())
                .andExpect(jsonPath("$.body.userAgreement").doesNotExist());


        // 카카오 로그인
        mockMvc.perform(post("/api/auth/kakao/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(true))
                .andExpect(jsonPath("$.body.accessToken").doesNotExist())
                .andExpect(jsonPath("$.body.jwtToken").exists())
                .andExpect(jsonPath("$.body.userAgreement").doesNotExist());
    }

    @Test
    @Order(2)
    @DisplayName("구글 회원가입 및 로그인")
    @Disabled
    void processGoogleAuthentication_processSignUp() throws Exception {
        // 구글 회원 인증 요청
        mockMvc.perform(post("/api/auth/google/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(false))
                .andExpect(jsonPath("$.body.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.body.jwtToken").doesNotExist())
                .andExpect(jsonPath("$.body.userAgreement").exists())
                .andExpect(jsonPath("$.body.userAgreement.id").value(2));

        // 회원가입 요청 파라미터
        JSONObject jsonObject = new JSONObject()
                .put("accessToken", ACCESS_TOKEN)
                .put("username", "username2")
                .put("profileImage", "profile 이미지 URI")
                .put("genderType", "FEMALE")
                .put("birth", "2000.01.01")
                .put("locationType", "인천")
                .put("careerType", "INTERMEDIATE")
                .put("introduction", "자기소개입니다.");

        // 회원가입 요청
        mockMvc.perform(post("/api/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(true))
                .andExpect(jsonPath("$.body.accessToken").doesNotExist())
                .andExpect(jsonPath("$.body.jwtToken").exists())
                .andExpect(jsonPath("$.body.userAgreement").doesNotExist());

        // 구글 로그인
        mockMvc.perform(post("/api/auth/google/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.code").value(200))
                .andExpect(jsonPath("$.header.reason").value("success"))
                .andExpect(jsonPath("$.body.registered").value(true))
                .andExpect(jsonPath("$.body.accessToken").doesNotExist())
                .andExpect(jsonPath("$.body.jwtToken").exists())
                .andExpect(jsonPath("$.body.userAgreement").doesNotExist());
    }
}