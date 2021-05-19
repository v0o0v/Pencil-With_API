package com.pencilwith.apiserver.working.service;

import com.pencilwith.apiserver.domain.entity.Project;
import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.domain.exception.BadRequestException;
import com.pencilwith.apiserver.domain.repository.ProjectRepository;
import com.pencilwith.apiserver.domain.repository.UserRepository;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;
import com.pencilwith.apiserver.working.mapper.ProjectMapper;
import com.pencilwith.apiserver.working.mapper.ProjectTitleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ProjectTitleResponse> selectAllTitles() {
        // 현재 사용자의 프로젝트 리스트 조회
        User curUser = checkUser();

        List<Project> projects = curUser.getOwnerProjectList();
        if(projects.isEmpty())
            throw new BadRequestException("해당 프로젝트들이 존재하지 않습니다.");

        return ProjectTitleMapper.toDto(projects);
    }

    @Transactional(readOnly = true)
    public ProjectResponse selectProject(Long id) {
        // 현재 사용자의 프로젝트를 조회함
        Project project = checkProject(id, "해당 프로젝트를 조회할 수 없습니다.");

        return ProjectMapper.toDto(project);
    }

    @Transactional
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        checkProject(id, "해당 프로젝트를 변경할 수 없습니다.");
        Project newProject = projectRepository.save(ProjectMapper.toEntity(projectRequest));
        return ProjectMapper.toDto(newProject);
    }

    @Transactional
    public void deleteProject(Long id) {
        checkProject(id, "해당 프로젝트를 삭제할 수 없습니다.");
        // 사용자의 프로젝트 중, 해당하는 프로젝트 삭제
        projectRepository.deleteById(id);
    }

    private Project checkProject(Long id, String msg) {
        User curUser = checkUser();

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("해당 프로젝트가 존재하지 않습니다."));

        User Owner = project.getOwner();
        if(Owner.getId().equals(curUser.getId()))
            throw new BadRequestException(msg);

        return project;
    }

    private User checkUser() {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getUsername())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 사용자입니다."));
    }
}
