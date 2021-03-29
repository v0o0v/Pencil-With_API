package com.pencilwith.apiserver.assembler;

import com.pencilwith.apiserver.controller.CrewController;
import com.pencilwith.apiserver.model.entity.CrewPost;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CrewAssembler implements RepresentationModelAssembler<CrewPost, EntityModel<CrewPost>> {
    // 작성자에 한해 지원글 링크 안보이도록 처리
    @Override
    public EntityModel<CrewPost> toModel(CrewPost crewPost) {
        return EntityModel.of(crewPost,
                linkTo(methodOn(CrewController.class).findCrewPostById(crewPost.getId())).withSelfRel(),
                linkTo(methodOn(CrewController.class).applyCrewPost(crewPost.getId())).withRel("apply-crew"),
                linkTo(CrewController.class).withRel("crews"));
    }
}
