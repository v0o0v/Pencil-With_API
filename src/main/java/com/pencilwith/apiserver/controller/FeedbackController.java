package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.assembler.FeedPostAssembler;
import com.pencilwith.apiserver.model.entity.FeedComment;
import com.pencilwith.apiserver.model.entity.FeedPost;
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
@RequestMapping(value = "/feedbacks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedPostAssembler feedPostAssembler;

    @GetMapping
    public CollectionModel<EntityModel<FeedPost>> allFeedBacks() {
        // 피드백 및 댓글을 불러옴
        List<FeedPost> feedPosts = new ArrayList<>();

        List<EntityModel<FeedPost>> feedPostList = feedPosts
                .stream()
                .map(feedPostAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(feedPostList,
                linkTo(FeedbackController.class).withSelfRel(),
                linkTo(FeedbackController.class).withRel("create-feedback"));
    }

    @GetMapping("{id}")
    public EntityModel<FeedPost> findFeedById(@PathVariable Long id) {
        // 피드백 하나를 조회
        FeedPost feedPost = new FeedPost();

        return feedPostAssembler.toModel(feedPost);
    }

    @PostMapping
    public ResponseEntity<EntityModel<FeedPost>> createFeedBack(@RequestBody FeedPost feedPost) {
        // 피드백 생성
        FeedPost newFeedPost = feedPost;
        URI location = linkTo(FeedbackController.class).toUri();

        return ResponseEntity.created(location).body(feedPostAssembler.toModel(feedPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FeedPost>> updateFeedBack(@PathVariable Long id, @RequestBody FeedPost feedPost) {
        // 피드백 업데이트
        FeedPost updateFeedPost = feedPost;

        return ResponseEntity.ok(feedPostAssembler.toModel(feedPost));
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Link> deleteFeedBacks(@PathVariable Long id) {
        // DB 에서 데이터 삭제

        return ResponseEntity.ok(linkTo(FeedbackController.class).withRel("feedbacks"));
    }

    @PostMapping("/{id}/report")
    public ResponseEntity<EntityModel<FeedPost>> reportFeedBack(@PathVariable Long id, @RequestBody FeedPost feedPost) {
        // 신고 처리
        FeedPost feedReport = feedPost;

        return ResponseEntity.ok(feedPostAssembler.toModel(feedReport));
    }

    @GetMapping("/{id}/comments/{c_id}")
    public ResponseEntity<EntityModel<FeedComment>> findComment(@PathVariable Long id, @PathVariable Long c_id) {
        // 댓글 조회
        FeedComment feedComment = new FeedComment();

        EntityModel<FeedComment> findFeedComment = EntityModel.of(feedComment,
                linkTo(methodOn(FeedbackController.class).findComment(id, feedComment.getId())).withSelfRel(),
                linkTo(FeedbackController.class).slash(id).slash("comments").withRel("create-comment"),
                linkTo(FeedbackController.class).withRel("feedbacks"));

        return ResponseEntity.ok(findFeedComment);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<EntityModel<FeedComment>> createComment(@PathVariable Long id, @RequestBody FeedComment feedComment) {
        // 댓글 생성
        FeedComment createComment = feedComment;

        // location으로 전체 피드백 링크 지정
        URI location = linkTo(FeedbackController.class).toUri();

        EntityModel<FeedComment> newFeedComment = EntityModel.of(createComment,
                linkTo(methodOn(FeedbackController.class).findComment(id, createComment.getId())).withSelfRel(),
                linkTo(FeedbackController.class).slash(id).slash("comments").withRel("create-comment"),
                linkTo(FeedbackController.class).withRel("feedbacks"));

        return ResponseEntity.created(location).body(newFeedComment);
    }
}
