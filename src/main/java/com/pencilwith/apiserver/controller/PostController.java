package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.Post;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> allPosts() {
        // DB 에서 데이터를 가져옴
        List<Post> postList = new ArrayList<>();

        return postList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post findPostById(@PathVariable Long id) {
        // DB 에서 데이터를 가져옴
        Post post = new Post();

        return post;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@RequestBody Post post) {
        // DB에 저장
        Post newPost = post;

        return newPost;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        // DB에 업데이트
        Post updatePost = post;

        return updatePost;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long id) {
        // DB 에서 데이터 삭제
    }
}
