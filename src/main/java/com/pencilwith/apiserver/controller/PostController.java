package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.Novel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
public class PostController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Novel> allNovels(@AuthenticationPrincipal User user) {
        // DB 에서 데이터를 가져옴
        List<Novel> novelList = new ArrayList<>();

        return novelList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Novel findNovelById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        // DB 에서 데이터를 가져옴
        Novel novel = new Novel();

        return novel;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Novel updateNovel(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody Novel novel) {
        // DB에 업데이트
        Novel updateNovel = novel;

        return updateNovel;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNovel(@PathVariable Long id) {
        // DB 에서 데이터 삭제
    }
}
