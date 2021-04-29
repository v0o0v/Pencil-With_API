package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTitleMapper {
    public static List<ProjectTitleResponse> toDto(List<Project> projects) {
        return projects.stream().map(project -> ProjectTitleResponse.builder()
                                .ownerId(project.getOwner().getId())
                                .projectId(project.getId())
                                .title(project.getTitle())
        .build()).collect(Collectors.toList());
    }
}
