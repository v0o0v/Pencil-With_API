package com.pencilwith.apiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class SignUpControllerTest extends IntegrationTestSetup {

    @Test
    @DisplayName("닉네임 중복 체크")
    void isNickNameDuplicated() throws Exception {
        mockMvc.perform(get("/api/sign-up/nickname/duplication")
                .param("nickName", "test1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("username", "username1")
                .put("password", "password")
                .put("nickName", "test1")
                .put("profileImage", "https://www.google.com/url?sa=i&url=https%3A%2F%2Ffirpeng.tistory.com%2F103&psig=AOvVaw2eQNj_SndJ_UcO5fS-SsSk&ust=1617196831404000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMDSgOmN2O8CFQAAAAAdAAAAABAD")
                .put("genderType", "MALE")
                .put("birth", "2021.03.30")
                .put("locationType", "SEOUL")
                .put("careerType", "SENIOR")
                .put("introduction", "자기소개");

        mockMvc.perform(post("/api/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

}