package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;

import java.util.HashSet;

public class ProjectMapper {
    public static ProjectResponse toDto(Project project) {
        return ProjectResponse.builder()
                .ownerId(project.getOwner().getId())
                .projectId(project.getId())
                .title(project.getTitle())
                .chapterList(ChapterMapper.toDto(new HashSet<>(project.getChapterList())))
                .build();
    }

    public static Project toEntity(ProjectRequest projectRequest) {
        return Project.builder()
                .id(projectRequest.getProjectId())
                .owner(User.builder()
                        .id(projectRequest.getOwnerId())
                        .build())
                .crewList(projectRequest.getCrewList())
                .chapterList(ChapterMapper.toEntity(projectRequest.getChapterList()))
                .title(projectRequest.getTitle())
                .build();
    }
}
