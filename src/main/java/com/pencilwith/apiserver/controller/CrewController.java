package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.CrewPost;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/crews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
public class CrewController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CrewPost> allCrewPosts() {
        // 모집글 전체 조회
        List<CrewPost> crewPostList = new ArrayList<>();

        return crewPostList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CrewPost findCrewPostById(@PathVariable Long id) {
        // 모집글 하나를 조회
        CrewPost crewPost = new CrewPost();

        return crewPost;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CrewPost createCrewPost(@RequestBody CrewPost crewPost) {
        // 모집글 생성
        CrewPost newCrewPost = crewPost;

        return newCrewPost;
    }

    @PostMapping("{id}/apply")
    @ResponseStatus(HttpStatus.OK)
    public CrewPost applyCrewPost(@PathVariable Long id) {
        // 지원 프로세스 처리후, 결과 반환
        CrewPost applyCrewPost = new CrewPost();

        return applyCrewPost;
    }

    @PostMapping("/{id}/report")
    @ResponseStatus(HttpStatus.OK)
    public CrewPost reportCrewPost(@PathVariable Long id) {
        // 신고 처리 프로세스
        CrewPost crewPost = new CrewPost();

        return crewPost;
    }
}
