package com.pencilwith.apiserver.working.controller;

import com.pencilwith.apiserver.domain.entity.User;
import com.pencilwith.apiserver.working.dto.project.ProjectRequest;
import com.pencilwith.apiserver.working.dto.project.ProjectResponse;
import com.pencilwith.apiserver.working.dto.project.ProjectTitleResponse;
import com.pencilwith.apiserver.working.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;

    @ApiOperation(value = "소설 리스트 조회",
                  notes = "My 소설의 전체 리스트를 조회합니다. id와 제목만 출력됩니다.")
    @GetMapping("/names")
    public ResponseEntity<List<ProjectTitleResponse>> getTitles(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.selectAllTitles(user));
    }

    @ApiOperation(value = "소설 한건 조회",
            notes = "My 소설 한 건을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@RequestParam Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.selectProject(id, user));
    }

    @ApiOperation(value = "소설 발행",
            notes = "입력받은 데이터를 기반으로 My 소설을 한 건 발행합니다.")
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        return ResponseEntity.ok(projectService.createProject(projectRequest));
    }

    @ApiOperation(value = "소설 수정",
            notes = "입력받은 데이터를 기반으로 특정 My 소설을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@RequestParam Long id, @RequestBody ProjectRequest projectRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.updateProject(id, projectRequest, user));
    }

    @ApiOperation(value = "소설 삭제",
            notes = "특정 My 소설을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@RequestParam Long id, @AuthenticationPrincipal User user) {
        projectService.deleteProject(id, user);
        return ResponseEntity.ok().build();
    }

}
