package com.pencilwith.apiserver.controller;

import com.pencilwith.apiserver.model.entity.FeedComment;
import com.pencilwith.apiserver.model.entity.FeedPost;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/feedbacks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaTypes.HAL_JSON_VALUE)
public class FeedbackController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FeedPost> allFeedBacks(@AuthenticationPrincipal User user) {
        // 피드백 및 댓글을 불러옴
        List<FeedPost> feedPostList = new ArrayList<>();

        return feedPostList;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedPost findFeedById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        // 피드백 하나를 조회
        FeedPost feedPost = new FeedPost();

        return feedPost;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedPost createFeedBack(@RequestBody FeedPost feedPost) {
        // 피드백 생성
        FeedPost newFeedPost = feedPost;

        return newFeedPost;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedPost updateFeedBack(@PathVariable Long id, @RequestBody FeedPost feedPost, @AuthenticationPrincipal User user) {
        // 피드백 업데이트
        FeedPost updateFeedPost = feedPost;

        return updateFeedPost;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFeedBacks(@PathVariable Long id, @AuthenticationPrincipal User user) {
        // 피드백 삭제

    }

    @PostMapping("/{id}/report")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedPost reportFeedBack(@PathVariable Long id, @RequestBody FeedPost feedPost) {
        // 신고 처리
        FeedPost feedReport = feedPost;

        return feedPost;
    }

    @GetMapping("/{id}/comments/{c_id}")
    @ResponseStatus(HttpStatus.OK)
    public FeedComment findComment(@PathVariable Long id, @PathVariable Long c_id) {
        // 댓글 조회
        FeedComment feedComment = new FeedComment();

        return feedComment;
    }

    @PostMapping("/{id}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedComment createComment(@PathVariable Long id, @RequestBody FeedComment feedComment) {
        // 댓글 생성
        FeedComment createComment = feedComment;

       return createComment;
    }
}
