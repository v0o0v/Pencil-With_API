package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.working.dto.project.ProjectDto;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;

public class ProjectMapper {
    public static Project toEntity(ProjectRequest projectRequest) {
        return new Project();
    }

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .ownerId(project.getOwner().getId())
                .projectId(project.getId())
                .title(project.getTitle())
                .chapterList(ChapterMapper.toDto(project.getChapterList()))
                .build();
    }
}
