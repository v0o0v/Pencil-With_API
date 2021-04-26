package com.pencilwith.apiserver.working.service;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;
import com.pencilwith.apiserver.working.mapper.ProjectMapper;
import com.pencilwith.apiserver.working.mapper.ProjectTitleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<ProjectTitleResponse> selectAllTitles(User user) {
        // 현재 사용자의 프로젝트 리스트 조회
        List<Project> projects = user.getProject();
        return ProjectTitleMapper.toDto(projects);
    }

    public ProjectResponse selectProject(Long id, User user) {
        // 현재 사용자의 프로젝트를 조회함
        Project project = projectRepository.findProjectByOwnerId(user.getId());
        return ProjectMapper.toDto(project);
    }

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest, User user) {
        checkUser(id, user);
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    public void deleteProject(Long id, User user) {
        checkUser(id, user);
        // 사용자의 프로젝트 중, 해당하는 프로젝트 삭제
        projectRepository.deleteById(id);
    }

    private void checkUser(Long id, User user) {
        Project project = projectRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        User Owner = project.getOwner();
        if(Owner.getId() != user.getId())
            throw new NullPointerException();
    }
}
