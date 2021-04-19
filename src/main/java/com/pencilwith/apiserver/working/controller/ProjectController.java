package com.pencilwith.apiserver.working.controller;

import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.working.dto.project.ProjectDto;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;
import com.pencilwith.apiserver.working.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/names")
    public List<ProjectTitleResponse> getTitles(@AuthenticationPrincipal User user) {
        return projectService.selectAllTitles(user);
    }

    @GetMapping("/{id}")
    public ProjectDto getProject(@RequestParam Long id, @AuthenticationPrincipal User user) {
        return projectService.selectProject(id, user);
    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @PutMapping
    public ProjectDto updateProject(@RequestBody ProjectRequest projectRequest, @AuthenticationPrincipal User user) {
        return projectService.updateProject(projectRequest, user);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@RequestParam Long id, @AuthenticationPrincipal User user) {
        projectService.deleteProject(id, user);
    }

}
