package com.pencilwith.apiserver.assembler;

import com.pencilwith.apiserver.controller.PostController;
import com.pencilwith.apiserver.model.entity.Post;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {
    // 작성자에 한해 수정 링크 뜨도록 처리
    @Override
    public EntityModel<Post> toModel(Post post) {
        return EntityModel.of(post,
                linkTo(methodOn(PostController.class).findPostById(post.getId())).withSelfRel(),
                linkTo(PostController.class).slash(post.getId()).withRel("update-post"),
                linkTo(PostController.class).withRel("posts"));
    }
}
