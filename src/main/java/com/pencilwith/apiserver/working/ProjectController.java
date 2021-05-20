package com.pencilwith.apiserver.working;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
    private final ProjectService projectService;

    @ApiOperation(value = "나의 프로젝트 리스트 조회",
                  notes = "나의 프로젝트 리스트와 Crew로 참여중인 프로젝트 리스트를 반환합니다.")
    @GetMapping("/my")
    public ResponseEntity<?> getMyProjectList() {
        return ResponseEntity.ok(projectService.getMyProjectList());
    }

//    @ApiOperation(value = "소설 한건 조회",
//            notes = "My 소설 한 건을 조회합니다.")
//    @GetMapping("/{id}")
//    public ResponseEntity<ProjectResponse> getProject(@RequestParam Long id) {
//        return ResponseEntity.ok(projectService.selectProject(id));
//    }
//
//    @ApiOperation(value = "소설 발행",
//            notes = "입력받은 데이터를 기반으로 My 소설을 한 건 발행합니다.")
//    @PostMapping
//    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectRequest) {
//        return ResponseEntity.ok(projectService.createProject(projectRequest));
//    }
//
//    @ApiOperation(value = "소설 수정",
//            notes = "입력받은 데이터를 기반으로 특정 My 소설을 수정합니다.")
//    @PutMapping("/{id}")
//    public ResponseEntity<ProjectResponse> updateProject(@RequestParam Long id, @RequestBody ProjectRequest projectRequest) {
//        return ResponseEntity.ok(projectService.updateProject(id, projectRequest));
//    }
//
//    @ApiOperation(value = "소설 삭제",
//            notes = "특정 My 소설을 삭제합니다.")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteProject(@RequestParam Long id) {
//        projectService.deleteProject(id);
//        return ResponseEntity.ok().build();
//    }

}
