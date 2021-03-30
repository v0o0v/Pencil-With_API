package com.pencilwith.apiserver.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class SignUpControllerTest extends IntegrationTestSetup {

    @Test
    void signUp() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("nickName", "test1")
                .put("gender", "MALE")
                .put("birth", "2021.03.30")
                .put("location", "SEOUL")
                .put("career", "SENIOR")
                .put("introduction", "자기소개");

        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

}