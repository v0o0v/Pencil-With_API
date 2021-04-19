package com.pencilwith.apiserver.working.service;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.working.dto.project.ProjectDto;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;
import com.pencilwith.apiserver.working.mapper.ProjectMapper;
import com.pencilwith.apiserver.working.mapper.ProjectTitleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectTitleResponse> selectAllTitles(User user) {
        // 현재 사용자의 프로젝트 리스트 조회
        List<Project> projects = user.getProject();
        return ProjectTitleMapper.toDto(projects);
    }

    public ProjectDto selectProject(Long id, User user) {
        // 현재 사용자의 프로젝트를조회함
        Project project = projectRepository.findProjectByOwnerId(user.getId());
        return ProjectMapper.toDto(project);
    }

    public ProjectDto createProject(ProjectRequest projectRequest) {
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    public ProjectDto updateProject(ProjectRequest projectRequest, User user) {
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    public void deleteProject(Long id, User user) {
        // 사용자의 프로젝트 중, 해당하는 프로젝트 삭제
        projectRepository.deleteById(id);
    }
}
