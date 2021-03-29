package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.assembler.PostAssembler;
import com.pencilwith.apiserver.model.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
@RequestMapping(value = "/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class PostController {
    private final PostAssembler postAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Post>> allPosts() {
        // DB 에서 데이터를 가져옴
        List<Post> posts = new ArrayList<>();

        List<EntityModel<Post>> postList = posts
                .stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(postList,
                linkTo(PostController.class).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Post> findPostById(@PathVariable Long id) {
        // DB 에서 데이터를 가져옴
        Post post = new Post();

        return postAssembler.toModel(post);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Post>> createPost(@RequestBody Post post) {
        // DB에 저장
        Post newPost = post;
        URI location = linkTo(methodOn(PostController.class).findPostById(post.getId())).toUri();

        return ResponseEntity.created(location).body(postAssembler.toModel(newPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Post>> updatePost(@PathVariable Long id, @RequestBody Post post) {
        // DB에 업데이트
        Post updatePost = post;

        return ResponseEntity.ok(postAssembler.toModel(updatePost));
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Link> deletePost(@PathVariable Long id) {
        // DB 에서 데이터 삭제

        return ResponseEntity.ok(linkTo(PostController.class).withRel("posts"));
    }
}
