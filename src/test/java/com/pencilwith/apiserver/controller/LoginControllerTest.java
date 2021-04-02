package com.pencilwith.apiserver.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pencilwith.apiserver.IntegrationTestSetup;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class LoginControllerTest extends IntegrationTestSetup {

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        JSONObject jsonObject = new JSONObject()
                .put("username", "username2")
                .put("password", "password");

        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }
}