package com.pencilwith.apiserver.working;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "Project 한건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @ApiOperation(value = "신규 Project 생성")
    @PostMapping
    public ResponseEntity<?> createProject(@Validated @RequestBody ProjectControllerRequestDTO.ProjectCreateRequestDTO dto) {
        return ResponseEntity.ok(projectService.createProject(dto.getTitle()));
    }

    @ApiOperation(value = "신규 Chapter 생성")
    @PostMapping("/{id}/chapter")
    public ResponseEntity<?> createChapter(@PathVariable Long id
            , @Validated @RequestBody ProjectControllerRequestDTO.ChapterCreateRequestDTO dto) {
        return ResponseEntity.ok(projectService.createChpter(id, dto.getTitle()));
    }

    @ApiOperation(value = "Chapter 배포")
    @PutMapping("/{projectId}/chapter/{chapterId}/publish")
    public ResponseEntity<?> publishChapter(
            @PathVariable Long projectId
            , @PathVariable Long chapterId) {
        return ResponseEntity.ok(projectService.publishChapter(projectId, chapterId));
    }

    @ApiOperation(value = "Chapter 컨텐트 수정")
    @PutMapping("/{projectId}/chapter/{chapterId}/content")
    public ResponseEntity<?> modifyChapterContent(
            @PathVariable Long projectId
            , @PathVariable Long chapterId
            , @RequestBody ProjectControllerRequestDTO.ChapterContentModifyRequestDTO dto
    ) {
        return ResponseEntity.ok(projectService.modifyChapterContent(projectId, chapterId, dto.getContent()));
    }


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
