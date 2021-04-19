package com.pencilwith.apiserver.working.dto.project;

import com.pencilwith.apiserver.domain.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectTitleResponse {
    private String ownerId;
    private Long projectId;
    private String title;

    public ProjectTitleResponse entityTo(Project project) {
      return ProjectTitleResponse.builder()
      .ownerId(project.getOwner().getId())
      .projectId(project.getId())
      .title(project.getTitle())
      .build();
    };
}
