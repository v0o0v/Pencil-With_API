package com.pencilwith.apiserver.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    private PostController postController;

    @Test
    @DisplayName("피드백이 없을시 모집글을 띄우는지")
    void TestSelectAll_CrewPosts() throws Exception {

    }

    @Test
    @DisplayName("작성자 한정 수정 링크 제공")
    void TestSelect_UpdateLink_ShouldThrowException() throws Exception {

    }
}
