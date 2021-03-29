package com.pencilwith.apiserver.assembler;

import com.pencilwith.apiserver.controller.FeedbackController;
import com.pencilwith.apiserver.model.entity.FeedPost;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeedPostAssembler implements RepresentationModelAssembler<FeedPost, EntityModel<FeedPost>> {
    // 작성자에 한해 업데이트 링크는 보이고, 신고 링크는 없어야 한다
    @Override
    public EntityModel<FeedPost> toModel(FeedPost feedPost) {
        return EntityModel.of(feedPost,
                linkTo(methodOn(FeedbackController.class).findFeedById(feedPost.getId())).withSelfRel(),
                linkTo(FeedbackController.class).slash(feedPost.getId()).slash("report").withRel("report-feedback"),
                linkTo(FeedbackController.class).slash(feedPost.getId()).withRel("update-feedback"),
                linkTo(FeedbackController.class).withRel("feedbacks"),
                linkTo(FeedbackController.class).slash(feedPost.getId()).slash("comments").withRel("create-comment")
        );
    }
}
