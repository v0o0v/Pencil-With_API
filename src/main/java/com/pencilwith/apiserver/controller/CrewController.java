package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.assembler.CrewAssembler;
import com.pencilwith.apiserver.model.entity.CrewPost;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/crews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class CrewController {
    private final CrewAssembler crewAssembler;

    // 작성자는 지원 링크 안보이도록 처리
    @GetMapping
    public CollectionModel<EntityModel<CrewPost>> allCrewPosts() {
        // 크루 모집글을 불러옴
        List<CrewPost> crewPosts = new ArrayList<>();

        List<EntityModel<CrewPost>> crewPostList = crewPosts
                .stream()
                .map(crewAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(crewPostList,
                linkTo(CrewController.class).withSelfRel(),
                linkTo(CrewController.class).withRel("create-crew"));
    }

    @GetMapping("/{id}")
    public EntityModel<CrewPost> findCrewPostById(@PathVariable Long id) {
        // 모집글 하나를 조회
        CrewPost crewPost = new CrewPost();

        return crewAssembler.toModel(crewPost);
    }

    @PostMapping
    public ResponseEntity<EntityModel<CrewPost>> createCrewPost(@RequestBody CrewPost crewPost) {
        // 모집글 생성
        CrewPost newCrewPost = crewPost;
        URI location = linkTo(methodOn(CrewController.class).findCrewPostById(crewPost.getId())).withSelfRel().toUri();

        return ResponseEntity.created(location).body(crewAssembler.toModel(newCrewPost));
    }

    @PostMapping("{id}/apply")
    public ResponseEntity<EntityModel<CrewPost>> applyCrewPost(@PathVariable Long id) {
        // 지원 프로세스 처리후, 결과 반환
        CrewPost applyCrewPost = new CrewPost();
        URI location = linkTo(methodOn(CrewController.class).findCrewPostById(applyCrewPost.getId())).withSelfRel().toUri();

        return ResponseEntity.created(location).body(crewAssembler.toModel(applyCrewPost));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<EntityModel<CrewPost>> reportCrewPost(@PathVariable Long id, @RequestBody CrewPost crewPost) {
        // 신고 처리 프로세스

        EntityModel<CrewPost> crewReport = EntityModel.of(crewPost,
                linkTo(methodOn(CrewController.class).findCrewPostById(crewPost.getId())).withSelfRel(),
                linkTo(CrewController.class).withRel("crews"));

        return ResponseEntity.ok(crewReport);
    }
}
