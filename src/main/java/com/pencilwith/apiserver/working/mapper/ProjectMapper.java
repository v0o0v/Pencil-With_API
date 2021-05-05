package com.pencilwith.apiserver.working.mapper;

import com.pencilwith.apiserver.domain.entity.Chapter;
import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
                .owner(projectRequest.getOwner())
                .crewList(projectRequest.getCrewList())
                .chapterList(ChapterMapper.toEntity(projectRequest.getChapterList()))
                .title(projectRequest.getTitle())
                .build();
    }
}
