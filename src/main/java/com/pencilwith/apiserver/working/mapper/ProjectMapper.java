package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;

public class ProjectMapper {
    public static Project toEntity(ProjectRequest projectRequest) {
        return new Project();
    }

    public static ProjectResponse toDto(Project project) {
        return ProjectResponse.builder()
                .ownerId(project.getOwner().getId())
                .projectId(project.getId())
                .title(project.getTitle())
                .chapterList(ChapterMapper.toDto(project.getChapterList()))
                .build();
    }
}
